local omgplayer
omgplayer = {
	constants = {
		-- Runtime qualifiers
		LOBBY = "LOBBY",
		MATCH = "MATCH",
		-- Message qualifiers
		SERVER_WELCOME_MESSAGE = "SERVER_WELCOME_MESSAGE",
		RUNTIME_ASSIGNMENT_MESSAGE = "RUNTIME_ASSIGNMENT_MESSAGE",
		MATCHMAKER_ASSIGNMENT_MESSAGE = "MATCHMAKER_ASSIGNMENT_MESSAGE",
		CONNECTION_UPGRADE_MESSAGE = "CONNECTION_UPGRADE_MESSAGE",
		DISCONNECTION_REASON_MESSAGE = "DISCONNECTION_REASON_MESSAGE",
		SERVER_OUTGOING_MESSAGE = "SERVER_OUTGOING_MESSAGE",
		-- Player events
		INITIALIZED = "INITIALIZED",
		SIGNED_UP = "SIGNED_UP",
		SIGNED_IN = "SIGNED_IN",
		GREETED = "GREETED",
		ASSIGNED = "ASSIGNED",
		MESSAGE_RECEIVED = "MESSAGE_RECEIVED",
		CONNECTION_UPGRADED = "CONNECTION_UPGRADED",
		PLAYER_FAILED = "PLAYER_FAILED",
		-- Disconnection reasons
		CLIENT_INACTIVITY = "CLIENT_INACTIVITY",
		INTERNAL_FAILURE = "INTERNAL_FAILURE",
		-- Miscellaneous
		WEBSOCKET_PROTOCOL = "WEBSOCKET",
	},
	settings = {
		debug = false,
		default_interval = nil,
		fast_interval = nil,
		iterations_threshold = nil,
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
				print(socket.gettime() .. " [OMGPLAYER] Triggered, event=" .. json.encode(event))
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
				qualifier = omgplayer.constants.INITIALIZED,
				body = {
				},
			}
			trigger:add_client_event(event)
		end,
		trigger_signed_up_event = function(trigger, user_id, password)
			local event = {
				qualifier = omgplayer.constants.SIGNED_UP,
				body = {
					user_id = user_id,
					password = password,
				},
			}
			trigger:add_client_event(event)
		end,
		trigger_signed_in_event = function(trigger, client_id)
			local event = {
				qualifier = omgplayer.constants.SIGNED_IN,
				body = {
					client_id = client_id,
				},
			}
			trigger:add_client_event(event)
		end,
		trigger_greeted_event = function(trigger, tenant_version_id, tenant_version_created)
			local event = {
				qualifier = omgplayer.constants.GREETED,
				body = {
					tenant_version_id = tenant_version_id,
					tenant_version_created = tenant_version_created,
				},
			}
			trigger:add_client_event(event)
		end,
		trigger_assigned_event = function(trigger, runtime_qualifier, runtime_id)
			local event = {
				qualifier = omgplayer.constants.ASSIGNED,
				body = {
					runtime_qualifier = runtime_qualifier,
					runtime_id = runtime_id,
				},
			}
			trigger:add_client_event(event)
		end,
		trigger_message_received_event = function(trigger, message_body)
			local event = {
				qualifier = omgplayer.constants.MESSAGE_RECEIVED,
				body = {
					message = message_body,
				}
			}
			trigger:add_client_event(event)
		end,
		trigger_connection_upgraded_event = function(trigger)
			local event = {
				qualifier = omgplayer.constants.CONNECTION_UPGRADED,
				body = {},
			}
			trigger:add_client_event(event)
		end,
		trigger_failed_event = function(trigger, reason)
			local event = {
				qualifier = omgplayer.constants.PLAYER_FAILED,
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
					print(socket.gettime() .. " [OMGPLAYER] Response, status=" .. response_status .. ", body=" .. response_body)
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
					print(socket.gettime() .. " [OMGPLAYER] Response, status=" .. response_status .. ", body=" .. response_body)
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
				print(socket.gettime() .. " [OMGPLAYER] Request, " .. method .. " " .. url .. ", body=" .. endoded_body)
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
					connection = server_url .. "/omgservers/v1/entrypoint/dispatcher/connection",
				}
			end,
			set_server_project = function(components, tenant_id, tenant_stage_id, tenant_stage_secret)
				components.server_project = {
					tenant_id = tenant_id,
					tenant_stage_id = tenant_stage_id,
					tenant_stage_secret = tenant_stage_secret,
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
			print(socket.gettime() .. " [OMGPLAYER] Url was changed, new_url=" .. url)
		end,
		use_project = function(server, tenant_id, tenant_stage_id, tenant_stage_secret)
			server.components:set_server_project(tenant_id, tenant_stage_id, tenant_stage_secret)
			print(socket.gettime() .. " [OMGPLAYER] Server project was set, tenant_id=" .. tenant_id .. ", tenant_stage_id=" .. tenant_stage_id .. ", tenant_stage_secret=" .. string.sub(tenant_stage_secret, 1, 4) .. "..")
		end,
		create_user = function(server, callback)
			assert(server.components.server_urls, "Component server_urls must be set")

			local request_url = server.components.server_urls.create_user
			local request_body = {}
			local response_handler = function(response_status, response_body)
				local user_id = response_body.user_id
				local password = response_body.password

				server.components:set_user_credentials(user_id, password);

				print(socket.gettime() .. " [OMGPLAYER] User was created, user_id=" .. user_id .. ", password=" .. string.sub(password, 1, 4) .. "..")
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

			print(socket.gettime() .. " [OMGPLAYER] Credentials were set, user_id=" .. user_id .. ", password=" .. string.sub(password, 1, 4) .. "..")
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
				print(socket.gettime() .. " [OMGPLAYER] Api token was received, token=" .. string.sub(raw_token, 1, 4) .. "..")
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
			local tenant_stage_id = server_components.server_project.tenant_stage_id
			local tenant_stage_secret = server_components.server_project.tenant_stage_secret

			local request_url = server_components.server_urls.create_client
			local request_body = {
				tenant_id = tenant_id,
				tenant_stage_id = tenant_stage_id,
				tenant_stage_secret = tenant_stage_secret
			}
			local response_handler = function(response_status, response_body)
				local client_id = response_body.client_id
				server.components:set_server_client(client_id)
				print(socket.gettime() .. " [OMGPLAYER] Server client was created, client_id=" .. client_id)
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
		ws_connect = function(server, ws_token, callback)
			local connection_url = server.components.server_urls.connection .. "?ws_token=" .. ws_token
			local params = {
				protocol = "omgservers"
			}

			print(socket.gettime() .. " [OMGSERVER] Connect websocket, url=" .. connection_url)

			local ws_connection = websocket.connect(connection_url, params, function(_, _, data)
				if data.event == websocket.EVENT_DISCONNECTED then
					print(socket.gettime() .. " [OMGSERVER] Websocket disconnected")

				elseif data.event == websocket.EVENT_CONNECTED then
					print(socket.gettime() .. " [OMGSERVER] Websocket connected")
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
		send_text_message = function(server, message)
			assert(server.components.server_client, "Component server_client must be created")
			assert(type(message) == "string", "Message has to be string")
			assert(server.components.connection, "Connection must be created")

			-- Send using upgraded connection
			websocket.send(server.components.connection.ws_connection, message, {
				type = websocket.DATA_TYPE_TEXT
			})
			
		end,
		send_binary_message = function(server, buffer)
			assert(server.components.server_client, "Component server_client must be created")
			assert(type(buffer) == "string", "Message has to be string")
			assert(server.components.connection, "Connection must be created")

			-- Send using upgraded connection
			websocket.send(server.components.connection.ws_connection, buffer, {
				type = websocket.DATA_BINARY_TEXT
			})
			
		end,
		send_service_message = function(server, message)
			assert(server.components.server_client, "Component server_client must be created")
			assert(type(message) == "string", "Message has to be string")

			-- Send using service command
			local message_id = server.components.server_client:generate_message_id()

			local outgoing_message = {
				id = message_id,
				qualifier = "CLIENT_OUTGOING_MESSAGE",
				body = {
					data = message
				}
			}
			server.components.server_client:add_outgoing_message(outgoing_message)
		end,
	},
	-- Flow
	flow = {
		components = {
			iteration_state = {
				iteration_timer = 0,
				iterations_in_vain = 0,
				fast_iterations = true
			},
			player_state = {
				greeted = false,
				lobby_id = nil,
				matchmaker_id = nil,
				match_id = nil,
				-- Methods
				set_greeted = function(player_state, greeted)
					player_state.greeted = greeted
				end,
				set_lobby_id = function(player_state, assigned_lobby_id)
					player_state.lobby_id = assigned_lobby_id
					player_state.match_id = nil
				end,
				set_matchmaker_id = function(player_state, assigned_matchmaker_id)
					player_state.matchmaker_id = assigned_matchmaker_id
				end,
				set_match_id = function(player_state, assigned_match_id)
					player_state.lobby_id = nil
					player_state.match_id = assigned_match_id
				end,
				get_runtime_id = function(player_state)
					return player_state.lobby_id or player_state.match_id
				end,
			},
			-- Methods
			set_server_version = function(components, tenant_version_id, tenant_version_created)
				components.server_version = {
					tenant_version_id = tenant_version_id,
					tenant_version_created = tenant_version_created,
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
				print(socket.gettime() .. " [OMGPLAYER] Incoming messages, incoming_message=" .. json.encode(incoming_message))
			end

			-- SERVER_WELCOME_MESSAGE is a first server message
			-- Server produces MATCHMAKER_ASSIGNMENT_MESSAGE and RUNTIME_ASSIGNMENT_MESSAGE concurrently
			-- So we trigger greeted event only when MATCHMAKER_ASSIGNMENT_MESSAGE and RUNTIME_ASSIGNMENT_MESSAGE were received

			if message_qualifier == omgplayer_constants.SERVER_WELCOME_MESSAGE then
				local tenant_version_id = incoming_message.body.tenant_version_id
				local tenant_version_created = incoming_message.body.tenant_version_created
				flow_components:set_server_version(tenant_version_id, tenant_version_created)

			elseif message_qualifier == omgplayer_constants.MATCHMAKER_ASSIGNMENT_MESSAGE then
				local matchmaker_id = incoming_message.body.matchmaker_id
				flow_components.player_state:set_matchmaker_id(matchmaker_id)

				if not flow_components.player_state.greeted and flow_components.player_state.lobby_id then
					flow_components.player_state:set_greeted(true)
					omgplayer.trigger:trigger_greeted_event(flow_components.server_version.tenant_version_id, flow_components.server_version.tenant_version_created)
				end

			elseif message_qualifier == omgplayer_constants.RUNTIME_ASSIGNMENT_MESSAGE then
				local runtime_id = incoming_message.body.runtime_id
				local runtime_qualifier = incoming_message.body.runtime_qualifier

				if runtime_qualifier == omgplayer_constants.LOBBY then
					flow_components.player_state:set_lobby_id(runtime_id)

					if flow_components.player_state.greeted then
						omgplayer.trigger:trigger_assigned_event(runtime_qualifier, runtime_id)
					else
						if flow_components.player_state.matchmaker_id then
							flow_components.player_state:set_greeted(true)
							omgplayer.trigger:trigger_greeted_event(flow_components.server_version.tenant_version_id, flow_components.server_version.tenant_version_created)
						end
					end

				elseif runtime_qualifier == omgplayer_constants.MATCH then
					flow_components.player_state:set_match_id(runtime_id)
					omgplayer.trigger:trigger_assigned_event(runtime_qualifier, runtime_id)

				else
					print(socket.gettime() .. " [OMGPLAYER] Unknown runtime qualifier was received, runtime_qualifier=" .. runtime_qualifier)
				end

			elseif message_qualifier == omgplayer_constants.SERVER_OUTGOING_MESSAGE then
				local message_body = incoming_message.body.message
				omgplayer.trigger:trigger_message_received_event(message_body)

			elseif message_qualifier == omgplayer_constants.CONNECTION_UPGRADE_MESSAGE then
				local upgrade_protocol = incoming_message.body.protocol
				if upgrade_protocol == omgplayer.constants.WEBSOCKET_PROTOCOL then
					local web_socket_config = incoming_message.body.web_socket_config
					local ws_token = web_socket_config.ws_token

					omgplayer.server:ws_connect(ws_token, function()
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
			local iteration_state = flow_components.iteration_state

			local iteration_timer = iteration_state.iteration_timer + dt
			local iteration_interval
			if iteration_state.fast_iterations then
				iteration_interval = omgplayer.settings.fast_interval
			else
				iteration_interval = omgplayer.settings.default_interval
			end

			if iteration_timer > iteration_interval then
				if omgplayer.http_client.components.client_state.waiting_for_response == false then
					omgplayer.server:interchange()
				end

				iteration_state.iteration_timer = 0

				local incoming_messages = server_components.server_client:pull_incoming_messages()

				-- Switch between default and fast interchange intervals
				if #incoming_messages > 0 then
					iteration_state.iterations_in_vain = 0
					if not iteration_state.fast_iterations then
						iteration_state.fast_iterations = true
						if omgplayer.settings.debug then
							print(socket.gettime() .. " [OMGPLAYER] Switched to fast interval")
						end
					end
				else
					local iterations_in_vain = iteration_state.iterations_in_vain
					if iterations_in_vain >= omgplayer.settings.iterations_threshold then
						if iteration_state.fast_iterations then
							iteration_state.fast_iterations = false
							if omgplayer.settings.debug then
								print(socket.gettime() .. " [OMGPLAYER] Switched to default interval")
							end
						end
					else
						iteration_state.iterations_in_vain = iterations_in_vain + 1
					end
				end
				
				for message_index, incoming_message in ipairs(incoming_messages) do
					flow:handle_message(incoming_message)
				end
			else
				iteration_state.iteration_timer = iteration_timer
			end
		end,
	},
	-- Methods
	init = function(self, options)
		assert(options.service_url, "Value service_url must be set")
		assert(options.tenant_id, "Value tenant_id must be set")
		assert(options.tenant_stage_id, "Value tenant_stage_id must be set")
		assert(options.tenant_stage_secret, "Value tenant_stage_secret must be set")
		assert(options.handler, "Handler must not be nil")
		
		self.settings.debug = options.debug or false
		self.settings.default_interval = options.default_interval or 2
		self.settings.fast_interval = options.fast_interval or self.settings.default_interval * 0.25
		assert(self.settings.default_interval > self.settings.fast_interval , "Fast interval should be less than default")

		self.settings.iterations_threshold = options.iterations_threshold or 12
		
		print(socket.gettime() .. " [OMGPLAYER] Setting")
		pprint(self.settings)

		self.server:use_url(options.service_url)
		self.server:use_project(options.tenant_id, options.tenant_stage_id, options.tenant_stage_secret)
		self.components:set_event_handler(options.handler)
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
	--[[
	{
		self,
		options = {
			service_url,
			tenant_id, 
			tenant_stage_id, 
			tenant_stage_secret, 
			handler, 
			debug, 
			default_interval,
			fast_interval,
			iterations_threshold
		}
	}
	]]
	init = function(self, options)
		assert(options, "Options must be set")
		
		omgplayer:init(options)
	end,
	sign_up = function(self)
		omgplayer.flow:sign_up()
	end,
	sign_in = function(self, user_id, password)
		assert(user_id, "Value user_id must be set")
		assert(password, "Value password must be set")

		omgplayer.flow:sign_in(user_id, password)
	end,
	send_text_message = function(self, message)
		assert(message, "Message must not be nil")
		omgplayer.server:send_text_message(message)
	end,
	send_binary_message = function(self, buffer)
		assert(buffer, "Buffer must not be nil")
		omgplayer.server:send_binary_message(buffer)
	end,
	send_service_message = function(self, message)
		assert(buffer, "Message must not be nil")
		omgplayer.server:send_service_message(message)
	end,
	update = function(self, dt)
		assert(dt, "Value dt must be set")

		omgplayer:update(dt)
	end,
}