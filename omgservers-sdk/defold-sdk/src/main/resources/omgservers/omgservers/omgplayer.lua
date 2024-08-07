local omgplayer
omgplayer = {
	constants = {
		-- Runtime qualifiers
		LOBBY_RUNTIME_QUALIFIER = "LOBBY",
		MATCH_RUNTIME_QUALIFIER = "MATCH",
		-- Message qualifiers
		SERVER_WELCOME_MESSAGE = "SERVER_WELCOME_MESSAGE",
		MATCHMAKER_ASSIGNMENT_MESSAGE = "MATCHMAKER_ASSIGNMENT_MESSAGE",
		RUNTIME_ASSIGNMENT_MESSAGE = "RUNTIME_ASSIGNMENT_MESSAGE",
		SERVER_OUTGOING_MESSAGE = "SERVER_OUTGOING_MESSAGE",
	},
	settings = {
		debug = true,
	},
	components = {
		set_event_handler = function(components, handler)
			components.event_handler = {
				handler = handler
			}
		end,
	},
	-- Event trigger
	trigger = {
		components = {
			player_events = {
				events = {},
				-- Methods
				add_event = function(player_events, event)
					player_events.events[#player_events.events + 1] = event
				end,
				pull_events = function(player_events)
					local events = player_events.events
					player_events.events = {}
					return events
				end,
			},
		},
		-- Methods
		add_player_event = function(trigger, event)
			if omgplayer.settings.debug then
				print("[OMGPLAYER] Triggered, event=" .. json.encode(event))
			end
			trigger.components.player_events:add_event(event)
		end,
		trigger_player_signed_up = function(trigger, user_id, password)
			local event = {
				qualifier = "player_signed_up",
				user_id = user_id,
				password = password,
			}
			trigger:add_player_event(event)
		end,
		trigger_player_signed_in = function(trigger, client_id)
			local event = {
				qualifier = "player_signed_in",
				client_id = client_id,
			}
			trigger:add_player_event(event)
		end,
		trigger_player_was_greeted = function(trigger, version_id, version_created)
			local event = {
				qualifier = "player_was_greeted",
				version_id = version_id,
				version_created = version_created,
			}
			trigger:add_player_event(event)
		end,
		trigger_player_was_assigned = function(trigger, runtime_qualifier, runtime_id)
			local event = {
				qualifier = "player_was_assigned",
				runtime_qualifier = runtime_qualifier,
				runtime_id = runtime_id,
			}
			trigger:add_player_event(event)
		end,
		trigger_service_message_received = function(trigger, message_body)
			local event = {
				qualifier = "service_message_received",
				message = message_body,
			}
			trigger:add_player_event(event)
		end,
		trigger_player_failed = function(trigger, reason)
			local event = {
				qualifier = "player_failed",
				reason = reason,
			}
			trigger:add_player_event(event)
		end,
		handle_events = function(trigger, event_handler)
			local player_events = trigger.components.player_events:pull_events()
			for event_index, player_event in ipairs(player_events) do
				event_handler(player_event)
			end
		end,
	},
	-- Http client
	http_client = {
		components = {
			client_state = {
				waiting_for_response = false,
			},
		},
		-- Methods
		build_final_handler = function(http_client, response_handler, failure_handler)
			return function(_, id, response)
				http_client.components.client_state.waiting_for_response = false

				local response_status = response.status
				local response_body = response.response

				local decoded_body
				if response_body then
					decoded_body = json.decode(response_body)
				end

				if response_status < 300 then
					response_handler(response_status, decoded_body)
				else
					failure_handler(response_status, decoded_body)
				end
			end
		end,
		build_retriable_handler = function(http_client, url, method, request_headers, request_body, response_handler, failure_handler, retries)
			return function(_, id, response)
				http_client.components.client_state.waiting_for_response = false

				local response_status = response.status
				local response_body = response.response

				local decoded_body
				if response_body then
					decoded_body = json.decode(response_body)
				end

				if response_status < 300 then
					response_handler(response_status, decoded_body)
				else
					local handler
					if retries > 0 then
						handler = http_client:build_retriable_handler(url, method, request_headers, request_body, response_handler, failure_handler, retries - 1)
					else
						handler = http_client:build_final_handler(response_handler, failure_handler)
					end

					http.request(url, method, handler, request_headers, request_body)
				end
			end
		end,
		request_server = function(http_client, url, request_body, response_handler, failure_handler, retries, request_token)
			assert(http_client.components.client_state.waiting_for_response == false, "Http client is waiting for response, skip request, url=" .. url)

			local request_headers = {
				["Content-Type"] = "application/json"
			}

			if request_token then
				request_headers["Authorization"] = "Bearer " .. request_token
			end

			local is_empty = next(request_body) == nil
			local endoded_body = json.encode(request_body, {
				encode_empty_table_as_object = is_empty
			})

			local method = "PUT"

			if omgplayer.settings.debug then
				print("[OMGPLAYER] Request, " .. method .. " " .. url .. ", body=" .. endoded_body)
			end

			http_client.components.client_state.waiting_for_response = true
			local retriable_handler = http_client:build_retriable_handler(url, method, request_headers, endoded_body, response_handler, failure_handler, retries)
			http.request(url, method, retriable_handler, request_headers, endoded_body)
		end
	},
	-- Server
	server = {
		components = {
			-- Methods
			set_server_urls = function(components, server_url)
				components.server_urls = {
					server_url = server_url,
					create_user = server_url .. "/omgservers/v1/entrypoint/player/request/create-user",
					create_token = server_url .. "/omgservers/v1/entrypoint/player/request/create-token",
					create_client = server_url .. "/omgservers/v1/entrypoint/player/request/create-client",
					interchange = server_url .. "/omgservers/v1/entrypoint/player/request/interchange",
					connection = server_url .. "/omgservers/v1/entrypoint/websocket/connection",
				}
			end,
			set_server_project = function(components, tenant_id, stage_id, stage_secret)
				components.server_project = {
					tenant_id = tenant_id,
					stage_id = stage_id,
					stage_secret = stage_secret,
				}
			end,
			set_user_credentials = function(components, user_id, password)
				components.user_credentials = {
					user_id = user_id,
					password = password,
				}
			end,
			set_api_token = function(components, raw_token)
				components.api_token = {
					raw_token = raw_token,
				}
			end,
			set_server_client = function(components, client_id)
				components.server_client = {
					client_id = client_id,
					message_counter = 0,
					outgoing_messages = {},
					incoming_messages = {},
					consumed_messages = {},
					-- Methods
					generate_message_id = function(server_client)
						local message_id = server_client.message_counter
						server_client.message_counter = server_client.message_counter + 1
						return message_id
					end,
					add_outgoing_message = function(server_client, message)
						server_client.outgoing_messages[#server_client.outgoing_messages + 1] = message
					end,
					add_incoming_message = function(server_client, message)
						server_client.incoming_messages[#server_client.incoming_messages + 1] = message
						server_client.consumed_messages[#server_client.consumed_messages + 1] = message.id
					end,
					pull_outgoing_messages = function(server_client)
						local outgoing_messages = server_client.outgoing_messages
						server_client.outgoing_messages = {}
						return outgoing_messages
					end,
					pull_incoming_messages = function(server_client)
						local incoming_messages = server_client.incoming_messages
						server_client.incoming_messages = {}
						return incoming_messages
					end,
					pull_consumed_messages = function(server_client)
						local consumed_messages = server_client.consumed_messages
						server_client.consumed_messages = {}
						return consumed_messages
					end,
				}
			end,
		},
		-- Methods
		use_url = function(server, url)
			server.components:set_server_urls(url)
			print("[OMGPLAYER] Url was changed, new_url=" .. url)
		end,
		use_project = function(server, tenant_id, stage_id, stage_secret)
			server.components:set_server_project(tenant_id, stage_id, stage_secret)
			print("[OMGPLAYER] Server project was set, tenant_id=" .. tenant_id .. ", stage_id=" .. stage_id .. ", stage_secret=" .. string.sub(stage_secret, 1, 4) .. "..")
		end,
		create_user = function(server, callback)
			assert(server.components.server_urls, "Component server_urls must be set")

			local request_url = server.components.server_urls.create_user
			local request_body = {}
			local response_handler = function(response_status, response_body)
				local user_id = response_body.user_id
				local password = response_body.password

				server.components:set_user_credentials(user_id, password);

				print("[OMGPLAYER] User was created, user_id=" .. user_id .. ", password=" .. string.sub(password, 1, 4) .. "..")
				if callback then
					callback(user_id, password)
				end
			end
			local failure_handler = function(response_status, response_body)
				local inlined_body
				if response_body then
					inlined_body = json.encode(response_body)
				end
				omgplayer.trigger:trigger_player_failed("user was not created, response_status=" .. response_status .. ", response_body=" .. inlined_body)
			end

			omgplayer.http_client:request_server(request_url, request_body, response_handler, failure_handler, 2)
		end,
		use_user = function(server, user_id, password)
			server.components:set_user_credentials(user_id, password);

			print("[OMGPLAYER] Credentials were set, user_id=" .. user_id .. ", password=" .. string.sub(password, 1, 4) .. "..")
		end,
		create_token = function(server, callback)
			assert(server.components.server_urls, "Component server_urls must be set")
			assert(server.components.user_credentials, "Component user_credentials must be set")

			local server_components = server.components

			local request_url = server_components.server_urls.create_token
			local request_body = {
				user_id = server_components.user_credentials.user_id,
				password = server_components.user_credentials.password,
			}
			local response_handler = function(response_status, decoded_body)
				local raw_token = decoded_body.raw_token
				server.components:set_api_token(raw_token)
				print("[OMGPLAYER] Api token was received, token=" .. string.sub(raw_token, 1, 4) .. "..")
				if callback then
					callback(raw_token)
				end
			end
			local failure_handler = function(response_status, decoded_body)
				local inlined_body
				if decoded_body then
					inlined_body = json.encode(decoded_body)
				end
				omgplayer.trigger:trigger_player_failed("token was not received, response_status=" .. response_status .. ", response_body=" .. inlined_body)
			end

			omgplayer.http_client:request_server(request_url, request_body, response_handler, failure_handler, 2)
		end,
		create_client = function(server, callback)
			assert(server.components.server_urls, "Component server_urls must be set")
			assert(server.components.server_project, "Component server_project must be set")
			assert(server.components.api_token, "Component api_token must be created")

			local server_components = server.components

			local tenant_id = server_components.server_project.tenant_id
			local stage_id = server_components.server_project.stage_id
			local stage_secret = server_components.server_project.stage_secret

			local request_url = server_components.server_urls.create_client
			local request_body = {
				tenant_id = tenant_id,
				stage_id = stage_id,
				stage_secret = stage_secret
			}
			local response_handler = function(response_status, response_body)
				local client_id = response_body.client_id
				server.components:set_server_client(client_id)
				print("[OMGPLAYER] Server client was created, client_id=" .. client_id)
				if callback then
					callback(client_id)
				end
			end
			local failure_handler = function(response_status, response_body)
				local inlined_body
				if response_body then
					inlined_body = json.encode(response_body)
				end
				omgplayer.trigger:trigger_player_failed("client was not created, response_status=" .. response_status .. ", response_body=" .. inlined_body)
			end

			local request_token = server_components.api_token.raw_token
			omgplayer.http_client:request_server(request_url, request_body, response_handler, failure_handler, 2, request_token)
		end,
		interchange = function(server)
			assert(server.components.server_urls, "Component server_urls must be created")
			assert(server.components.api_token, "Component api_token must be created")
			assert(server.components.server_client, "Component server_client must be created")

			local server_components = server.components

			local request_url = server_components.server_urls.interchange

			local request_body = {
				client_id = server_components.server_client.client_id,
				outgoing_messages = server_components.server_client:pull_outgoing_messages(),
				consumed_messages = server_components.server_client:pull_consumed_messages(),
			}
			local response_handler = function(response_status, response_body)
				local incoming_messages = response_body.incoming_messages

				for message_index = 1, #incoming_messages do
					local incoming_message = incoming_messages[message_index]
					server_components.server_client:add_incoming_message(incoming_message)
				end
			end
			local failure_handler = function(response_status, response_body)
				local inlined_body
				if response_body then
					inlined_body = json.encode(response_body)
				end
				omgplayer.trigger:trigger_player_failed("interchange failed, response_status=" .. response_status .. ", response_body=" .. inlined_body)
			end

			local request_token = server_components.api_token.raw_token
			omgplayer.http_client:request_server(request_url, request_body, response_handler, failure_handler, 4, request_token)
		end,
		send_client_message = function(server, message_data)
			assert(server.components.server_client, "Component server_client must be created")

			local server_components = server.components

			local message_id = server_components.server_client:generate_message_id()

			local outgoing_message = {
				id = message_id,
				qualifier = "CLIENT_OUTGOING_MESSAGE",
				body = {
					data = message_data
				}
			}
			server_components.server_client:add_outgoing_message(outgoing_message)
		end,
	},
	-- Flow
	flow = {
		components = {
			iterate_state = {
				timer = 0,
				interval = 1,
			},
			-- Methods
			set_server_version = function(components, version_id, version_created)
				components.server_version = {
					version_id = version_id,
					version_created = version_created,
				}
			end,
			set_client_assignments = function(components, lobby_id, matchmaker_id, match_id)
				components.client_assignments = {
					lobby_id = lobby_id,
					matchmaker_id = matchmaker_id,
					match_id = match_id,
					-- Methods
					set_lobby = function(client_assignments, assigned_lobby_id)
						client_assignments.lobby_id = assigned_lobby_id
						client_assignments.match_id = nil
					end,
					set_match = function(client_assignments, assigned_match_id)
						client_assignments.lobby_id = nil
						client_assignments.match_id = assigned_match_id
					end,
				}
			end,
		},
		-- Methods
		sign_up = function(flow, callback)
			omgplayer.server:create_user(function(user_id, password)
				omgplayer.trigger:trigger_player_signed_up(user_id, password)

				if callback then
					callback(user_id, password)
				end
			end)
		end,
		sign_in = function(flow, user_id, password, callback)
			omgplayer.server:use_user(user_id, password)

			omgplayer.server:create_token(function(raw_token)
				omgplayer.server:create_client(function(client_id)
					omgplayer.trigger:trigger_player_signed_in(client_id)

					if callback then
						callback(raw_token, client_id)
					end
				end)
			end)
		end,
		handle_message = function(flow, incoming_message)
			local flow_components = flow.components
			local omgplayer_constants = omgplayer.constants

			local message_qualifier = incoming_message.qualifier
			if omgplayer.settings.debug then
				print("[OMGPLAYER] Incoming messages, incoming_message=" .. json.encode(incoming_message))
			end

			-- SERVER_WELCOME_MESSAGE is a first server message
			-- Server produces MATCHMAKER_ASSIGNMENT_MESSAGE and RUNTIME_ASSIGNMENT_MESSAGE concurrently
			-- This handler triggers player_was_greeted event only after MATCHMAKER_ASSIGNMENT_MESSAGE and RUNTIME_ASSIGNMENT_MESSAGE were received

			if message_qualifier == omgplayer_constants.SERVER_WELCOME_MESSAGE then
				local version_id = incoming_message.body.version_id
				local version_created = incoming_message.body.version_created
				flow_components:set_server_version(version_id, version_created)
				
			elseif message_qualifier == omgplayer_constants.MATCHMAKER_ASSIGNMENT_MESSAGE then
				local matchmaker_id = incoming_message.body.matchmaker_id

				if not flow_components.client_assignments then
					flow_components:set_client_assignments(nil, matchmaker_id, nil)
				end
				
				if not flow_components.client_assignments.matchmaker_id then
					if flow_components.client_assignments.lobby_id then
						omgplayer.trigger:trigger_player_was_greeted(flow_components.server_version.version_id, flow_components.server_version.version_created)
					end
				end
				
				flow_components.client_assignments.matchmaker_id = matchmaker_id

			elseif message_qualifier == omgplayer_constants.RUNTIME_ASSIGNMENT_MESSAGE then
				local runtime_id = incoming_message.body.runtime_id
				local runtime_qualifier = incoming_message.body.runtime_qualifier
				if runtime_qualifier == omgplayer_constants.LOBBY_RUNTIME_QUALIFIER then
					if not flow_components.client_assignments then
						flow_components:set_client_assignments(runtime_id, nil, nil)
					end

					if not flow_components.client_assignments.lobby_id and not flow_components.client_assignments.match_id then
						if flow_components.client_assignments.matchmaker_id then
							omgplayer.trigger:trigger_player_was_greeted(flow_components.server_version.version_id, flow_components.server_version.version_created)
						end
					end
					
					flow_components.client_assignments:set_lobby(runtime_id)
					
					omgplayer.trigger:trigger_player_was_assigned(runtime_qualifier, runtime_id)

				elseif runtime_qualifier == omgplayer_constants.MATCH_RUNTIME_QUALIFIER then
					flow_components.client_assignments:set_match(runtime_id)
					omgplayer.trigger:trigger_player_was_assigned(runtime_qualifier, runtime_id)

				else
					print("[OMGPLAYER] Unknown runtime qualifier was received, runtime_qualifier=" .. runtime_qualifier)
				end

			elseif message_qualifier == omgplayer_constants.SERVER_OUTGOING_MESSAGE then
				local message_body = incoming_message.body.message
				omgplayer.trigger:trigger_service_message_received(message_body)
			end
		end,
		iterate = function(flow, dt)
			assert(omgplayer.server.components.server_client, "Component server_client must be created")

			local flow_components = flow.components
			local server_components = omgplayer.server.components

			local iterate_timer = flow_components.iterate_state.timer + dt
			local iterate_interval = flow_components.iterate_state.interval

			if iterate_timer > iterate_interval then
				if omgplayer.http_client.components.client_state.waiting_for_response == false then
					omgplayer.server:interchange()
				end

				flow_components.iterate_state.timer = iterate_timer - iterate_interval

				local incoming_messages = server_components.server_client:pull_incoming_messages()
				for message_index, incoming_message in ipairs(incoming_messages) do
					flow:handle_message(incoming_message)
				end
			else
				flow_components.iterate_state.timer = iterate_timer
			end
		end,
	},
	-- Methods
	init = function(self, server_url, tenant_id, stage_id, stage_secret, handler)
		self.server:use_url(server_url)
		self.server:use_project(tenant_id, stage_id, stage_secret)
		self.components:set_event_handler(handler)
	end,
	update = function(self, dt)
		assert(self.components.event_handler, "Component handler must be set")
		if self.server.components.server_client then
			self.flow:iterate(dt)
		end
		self.trigger:handle_events(self.components.event_handler.handler)
	end,
}

-- Entrypoint
return {
	init = function(self, server_url, tenant_id, stage_id, stage_secret, handler)
		omgplayer:init(server_url, tenant_id, stage_id, stage_secret, handler)
	end,
	sign_up = function(self, callback)
		omgplayer.flow:sign_up(callback)
	end,
	sign_in = function(self, user_id, password, callback)
		omgplayer.flow:sign_in(user_id, password, callback)
	end,
	update = function(self, dt)
		omgplayer:update(dt)
	end,
}