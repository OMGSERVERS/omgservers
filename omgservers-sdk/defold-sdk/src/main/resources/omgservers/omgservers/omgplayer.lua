local omgplayer
omgplayer = {
	constants = {
		-- Runtime qualifiers
		LOBBY_RUNTIME_QUALIFIER = "LOBBY",
		MATCH_RUNTIME_QUALIFIER = "MATCH",
		-- Message qualifiers
		SERVER_WELCOME_MESSAGE = "SERVER_WELCOME_MESSAGE",
		RUNTIME_ASSIGNMENT_MESSAGE = "RUNTIME_ASSIGNMENT_MESSAGE",
		MATCHMAKER_ASSIGNMENT_MESSAGE = "MATCHMAKER_ASSIGNMENT_MESSAGE",
		CONNECTION_UPGRADE_MESSAGE = "CONNECTION_UPGRADE_MESSAGE",
		DISCONNECTION_REASON_MESSAGE = "DISCONNECTION_REASON_MESSAGE",
		SERVER_OUTGOING_MESSAGE = "SERVER_OUTGOING_MESSAGE",
		-- Player events
		INITIALIZED_EVENT_QUALIFIER = "INITIALIZED",
		SIGNED_UP_EVENT_QUALIFIER = "SIGNED_UP",
		SIGNED_IN_EVENT_QUALIFIER = "SIGNED_IN",
		GREETED_EVENT_QUALIFIER = "GREETED",
		ASSSIGNED_EVENT_QUALIFIER = "ASSIGNED",
		MESSAGE_RECEIVED_EVENT_QUALIFIER = "MESSAGE_RECEIVED",
		CONNECTION_UPGRADED_EVENT_QUALIFIER = "CONNECTION_UPGRADED",
		FAILED_EVENT_QUALIFIER = "FAILED",
		-- Disonnection reasons
		CLIENT_INACTIVITY_DISCONNECTION_REASON = "CLIENT_INACTIVITY",
		INTERNAL_FAILURE_DISCONNECTION_REASON = "INTERNAL_FAILURE",
		-- Misilanious
		CONNECTION_UPGRADE_WEBSOCKET_PROTOCOL = "WEBSOCKET",
	},
	settings = {
		debug = false,
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
			client_events = {
				events = {},
				-- Methods
				add_event = function(client_events, event)
					client_events.events[#client_events.events + 1] = event
				end,
				pull_events = function(client_events)
					local events = client_events.events
					client_events.events = {}
					return events
				end,
			},
		},
		-- Methods
		add_client_event = function(trigger, event)
			if omgplayer.settings.debug then
				print("[OMGPLAYER] Triggered, event=" .. json.encode(event))
			end
			trigger.components.client_events:add_event(event)
		end,
		handle_events = function(trigger, event_handler)
			local client_events = trigger.components.client_events:pull_events()
			for event_index, player_event in ipairs(client_events) do
				event_handler(player_event)
			end
		end,
		trigger_initialized_event = function(trigger)
			local event = {
				qualifier = omgplayer.constants.INITIALIZED_EVENT_QUALIFIER,
				body = {
				},
			}
			trigger:add_client_event(event)
		end,
		trigger_signed_up_event = function(trigger, user_id, password)
			local event = {
				qualifier = omgplayer.constants.SIGNED_UP_EVENT_QUALIFIER,
				body = {
					user_id = user_id,
					password = password,
				},
			}
			trigger:add_client_event(event)
		end,
		trigger_signed_in_event = function(trigger, client_id)
			local event = {
				qualifier = omgplayer.constants.SIGNED_IN_EVENT_QUALIFIER,
				body = {
					client_id = client_id,
				},
			}
			trigger:add_client_event(event)
		end,
		trigger_greeted_event = function(trigger, version_id, version_created)
			local event = {
				qualifier = omgplayer.constants.GREETED_EVENT_QUALIFIER,
				body = {
					version_id = version_id,
					version_created = version_created,
				},
			}
			trigger:add_client_event(event)
		end,
		trigger_assigned_event = function(trigger, runtime_qualifier, runtime_id)
			local event = {
				qualifier = omgplayer.constants.ASSSIGNED_EVENT_QUALIFIER,
				body = {
					runtime_qualifier = runtime_qualifier,
					runtime_id = runtime_id,
				},
			}
			trigger:add_client_event(event)
		end,
		trigger_message_received_event = function(trigger, message_body)
			local event = {
				qualifier = omgplayer.constants.MESSAGE_RECEIVED_EVENT_QUALIFIER,
				body = {
					message = message_body,
				}
			}
			trigger:add_client_event(event)
		end,
		trigger_connection_upgraded_event = function(trigger)
			local event = {
				qualifier = omgplayer.constants.CONNECTION_UPGRADED_EVENT_QUALIFIER,
				body = {},
			}
			trigger:add_client_event(event)
		end,
		trigger_failed_event = function(trigger, reason)
			local event = {
				qualifier = omgplayer.constants.FAILED_EVENT_QUALIFIER,
				body = {
					reason = reason,
				},
			}
			trigger:add_client_event(event)
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

                if omgplayer.settings.debug then
                    print("[OMGPLAYER] Response, status=" .. response_status .. ", body=" .. response_body)
                end

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

                if omgplayer.settings.debug then
                    print("[OMGPLAYER] Response, status=" .. response_status .. ", body=" .. response_body)
                end

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
			set_connection = function(components, ws_connection)
				components.connection = {
					ws_connection = ws_connection,
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
				omgplayer.trigger:trigger_failed_event("user was not created, response_status=" .. response_status .. ", response_body=" .. inlined_body)
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
				omgplayer.trigger:trigger_failed_event("token was not received, response_status=" .. response_status .. ", response_body=" .. inlined_body)
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
				omgplayer.trigger:trigger_failed_event("client was not created, response_status=" .. response_status .. ", response_body=" .. inlined_body)
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
				omgplayer.trigger:trigger_failed_event("interchange failed, response_status=" .. response_status .. ", response_body=" .. inlined_body)
			end

			local request_token = server_components.api_token.raw_token
			omgplayer.http_client:request_server(request_url, request_body, response_handler, failure_handler, 4, request_token)
		end,
		ws_connect = function(server, runtime_id, ws_token, callback)
			local connection_url = server.components.server_urls.connection .. "?runtime_id=" .. runtime_id .. "&ws_token=" .. ws_token
			local params = {
				protocol = "omgservers"
			}

			print("[OMGSERVER] Connect websocket, url=" .. connection_url)

			local ws_connection = websocket.connect(connection_url, params, function(_, _, data)
				if data.event == websocket.EVENT_DISCONNECTED then
					print("[OMGSERVER] Websocket disconnected")

				elseif data.event == websocket.EVENT_CONNECTED then
					print("[OMGSERVER] Websocket connected")
					if callback then
						callback()
					end
					
				elseif data.event == websocket.EVENT_ERROR then
					omgplayer.trigger:trigger_failed_event("ws connection failed, message=" .. data.message)
					
				elseif data.event == websocket.EVENT_MESSAGE then
					omgplayer.trigger:trigger_message_received_event(data.message)
				end
			end)

			server.components:set_connection(ws_connection)
		end,
		send_message = function(server, message)
			assert(server.components.server_client, "Component server_client must be created")

			local server_components = server.components

			-- Send using connection if it exists
			if server_components.connection then
				local encoded_message = json.encode(message)
				websocket.send(server_components.connection.ws_connection, encoded_message, {
					type = websocket.DATA_TYPE_TEXT
				})
			else
				-- Send using service command
				local message_id = server_components.server_client:generate_message_id()

				local outgoing_message = {
					id = message_id,
					qualifier = "CLIENT_OUTGOING_MESSAGE",
					body = {
						data = message
					}
				}
				server_components.server_client:add_outgoing_message(outgoing_message)
			end
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
					get_runtime_id = function(client_assignments)
						return client_assignments.lobby_id or client_assignments.match_id
					end,
				}
			end,
		},
		-- Methods
		sign_up = function(flow)
			omgplayer.server:create_user(function(user_id, password)
				omgplayer.trigger:trigger_signed_up_event(user_id, password)
			end)
		end,
		sign_in = function(flow, user_id, password, callback)
			omgplayer.server:use_user(user_id, password)

			omgplayer.server:create_token(function(raw_token)
				omgplayer.server:create_client(function(client_id)
					omgplayer.trigger:trigger_signed_in_event(client_id)
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
						omgplayer.trigger:trigger_greeted_event(flow_components.server_version.version_id, flow_components.server_version.version_created)
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
							omgplayer.trigger:trigger_greeted_event(flow_components.server_version.version_id, flow_components.server_version.version_created)
						end
					end

					flow_components.client_assignments:set_lobby(runtime_id)

					omgplayer.trigger:trigger_assigned_event(runtime_qualifier, runtime_id)

				elseif runtime_qualifier == omgplayer_constants.MATCH_RUNTIME_QUALIFIER then
					flow_components.client_assignments:set_match(runtime_id)
					omgplayer.trigger:trigger_assigned_event(runtime_qualifier, runtime_id)

				else
					print("[OMGPLAYER] Unknown runtime qualifier was received, runtime_qualifier=" .. runtime_qualifier)
				end

			elseif message_qualifier == omgplayer_constants.SERVER_OUTGOING_MESSAGE then
				local message_body = incoming_message.body.message
				omgplayer.trigger:trigger_message_received_event(message_body)

			elseif message_qualifier == omgplayer_constants.CONNECTION_UPGRADE_MESSAGE then
				local upgrade_protocol = incoming_message.body.protocol
				if upgrade_protocol == omgplayer.constants.CONNECTION_UPGRADE_WEBSOCKET_PROTOCOL then
					local web_socket_config = incoming_message.body.web_socket_config
					local ws_token = web_socket_config.ws_token

					local runtime_id = flow_components.client_assignments:get_runtime_id()
					omgplayer.server:ws_connect(runtime_id, ws_token, function()
						omgplayer.trigger:trigger_connection_upgraded_event()
					end)
				else
					omgplayer.trigger:trigger_failed_event("unsupported connection upgrade protocol, protocol=" .. upgrade_protocol)
				end
			elseif message_qualifier == omgplayer_constants.DISCONNECTION_REASON_MESSAGE then
				local reason = incoming_message.body.reason
				omgplayer.trigger:trigger_failed_event("client was diconnected by server, reason=" .. reason)
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
	init = function(self, server_url, tenant_id, stage_id, stage_secret, handler, debug)
		self.settings.debug = debug or false
		print("[OMGPLAYER] Setting, debug=" .. tostring(self.settings.debug))
		
		self.server:use_url(server_url)
		self.server:use_project(tenant_id, stage_id, stage_secret)
		self.components:set_event_handler(handler)
		self.trigger:trigger_initialized_event()
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
	constants = omgplayer.constants,
	-- Methods
	init = function(self, server_url, tenant_id, stage_id, stage_secret, handler, debug)
		assert(server_url, "Value server_url must be set")
		assert(tenant_id, "Value tenant_id must be set")
		assert(stage_id, "Value stage_id must be set")
		assert(stage_secret, "Value stage_secret must be set")
		assert(handler, "Handler must not be nil")

		omgplayer:init(server_url, tenant_id, stage_id, stage_secret, handler, debug)
	end,
	sign_up = function(self)
		omgplayer.flow:sign_up()
	end,
	sign_in = function(self, user_id, password)
		assert(user_id, "Value user_id must be set")
		assert(password, "Value password must be set")

		omgplayer.flow:sign_in(user_id, password)
	end,
	send_message = function(self, message)
		assert(message, "Message must not be nil")

		omgplayer.server:send_message(message)
	end,
	update = function(self, dt)
		assert(dt, "Value dt must be set")

		omgplayer:update(dt)
	end,
}