local omgserver
omgserver = {
	constants = {
		-- Server exit codes
		ENVIRONMENT_EXIT_CODE = 1,
		TOKEN_EXIT_CODE = 2,
		CONFIG_EXIT_CODE = 3,
		API_EXIT_CODE = 4,
		WS_EXIT_CODE = 5,
		-- Server environment variables
		SERVICE_URL = "OMGSERVERS_URL",
		RUNTIME_ID = "OMGSERVERS_RUNTIME_ID",
		USER_PASSWORD = "OMGSERVERS_PASSWORD",
		RUNTIME_QUALIFIER = "OMGSERVERS_RUNTIME_QUALIFIER",
		-- Server event qualifiers
		SERVER_STARTED = "SERVER_STARTED",
		COMMAND_RECEIVED = "COMMAND_RECEIVED",
		MESSAGE_RECEIVED = "MESSAGE_RECEIVED",
		-- Runtime qualifiers
		LOBBY = "LOBBY",
		MATCH = "MATCH",
		-- Service command qualifiers
		INIT_RUNTIME = "INIT_RUNTIME",
		ADD_CLIENT = "ADD_CLIENT",
		ADD_MATCH_CLIENT = "ADD_MATCH_CLIENT",
		DELETE_CLIENT = "DELETE_CLIENT",
		HANDLE_MESSAGE = "HANDLE_MESSAGE",
		-- Runtime command qualifiers
		RESPOND_CLIENT = "RESPOND_CLIENT",
		SET_ATTRIBUTES = "SET_ATTRIBUTES",
		SET_PROFILE = "SET_PROFILE",
		MULTICAST_MESSAGE = "MULTICAST_MESSAGE",
		BROADCAST_MESSAGE = "BROADCAST_MESSAGE",
		KICK_CLIENT = "KICK_CLIENT",
		REQUEST_MATCHMAKING = "REQUEST_MATCHMAKING",
		STOP_MATCHMAKING = "STOP_MATCHMAKING",
		UPGRADE_CONNECTION = "UPGRADE_CONNECTION",
		-- Websockets protocol
		WEBSOCKET_PROTOCOL = "WEBSOCKET",
		BASE64_ENCODED = "B64",
		PLAIN_TEXT = "TXT",
	},
	settings = {
		debug = false,
		trace = false,
		default_interval = nil,
		fast_interval = nil,
		iterations_threshold = nil,
	},
	components = {
		server_state = {
			waiting_for_response = false,
			outgoing_commands = {},
			consumed_commands = {},
			server_events = {},
			iteration_timer = 0,
			iterations_in_vain = 0,
			fast_iterations = false,
			-- Methods
			add_outgoing_command = function(server_state, command)
				server_state.outgoing_commands[#server_state.outgoing_commands + 1] = command
			end,
			add_consumed_command = function(server_state, command)
				assert(command.id, "Consumed command must have id")
				server_state.consumed_commands[#server_state.consumed_commands + 1] = command.id
			end,
			add_server_event = function(server_state, event)
				server_state.server_events[#server_state.server_events + 1] = event
			end,
			pull_outgoing_commands = function(server_state)
				local outgoing_commands = server_state.outgoing_commands
				server_state.outgoing_commands = {}
				return outgoing_commands
			end,
			pull_consumed_commands = function(server_state)
				local consumed_commands = server_state.consumed_commands
				server_state.consumed_commands = {}
				return consumed_commands
			end,
			pull_server_events = function(server_state)
				local server_events = server_state.server_events
				server_state.server_events = {}
				return server_events
			end,
		},
		-- Methods
		set_event_handler = function(components, handler)
			components.event_handler = {
				handler = handler
			}
		end,
		set_server_environment = function(components, service_url, runtime_id, password, runtime_qualifier)
			components.server_environment = {
				service_url = service_url,
				runtime_id = runtime_id,
				password = password,
				runtime_qualifier = runtime_qualifier,
			}
		end,
		set_service_urls = function(components, service_url)
			components.service_urls = {
				create_token = service_url .. "/omgservers/v1/entrypoint/runtime/request/create-token",
				interchange = service_url .. "/omgservers/v1/entrypoint/runtime/request/interchange",
				connection = service_url .. "/omgservers/v1/entrypoint/websocket/connection",
			}
		end,
		set_tokens = function(components, api_token, ws_token)
			components.tokens = {
				api_token = api_token,
				ws_token = ws_token,
			}
		end,
		set_connection = function(components, ws_connection)
			components.connection = {
				ws_connection = ws_connection,
			}
		end,
	},
	-- Methods
	terminate_server = function(self, code, reason)
		print(socket.gettime() .. " [OMGSERVER] Terminated, code=" .. code .. ", reason=" .. reason)
		os.exit(code)
	end,
	build_handler = function(self, callback)
		return function(_, id, response)
			self.components.server_state.waiting_for_response = false

			local response_status = response.status
			local response_body = response.response

			if response_status < 300 then
				if self.settings.trace then
					print(socket.gettime() .. " [OMGSERVER] Response, status=" .. response_status .. ", body=" .. response_body)
				end

				local decoded_body
				if response_body then
					decoded_body = json.decode(response_body)
				end

				if callback then
					callback(response_status, decoded_body)
				end
			else
				self:terminate_server(self.constants.API_EXIT_CODE, "api request failed, status=" .. response_status .. ", body=" .. response_body)
			end
		end
	end,
	request_server = function(self, url, request_body, response_handler, request_token)
		assert(self.components.server_state, "Component server_state must be set")

		local request_headers = {
			["Content-Type"] = "application/json"
		}

		if request_token then
			request_headers["Authorization"] = "Bearer " .. request_token
		end

		local is_empty = next(request_body) == nil
		local encoded_body = json.encode(request_body, {
			encode_empty_table_as_object = is_empty
		})

		local method = "PUT"

		if self.settings.trace then
			local fast_iterations = self.components.server_state.fast_iterations
			print(socket.gettime() .. " [OMGSERVER] Request, " .. method .. " " .. url .. ", fast_iterations=" .. tostring(fast_iterations) .. ", body=" .. encoded_body)
		end

		self.components.server_state.waiting_for_response = true
		http.request(url, method, response_handler, request_headers, encoded_body)
	end,
	create_token = function(self, callback)
		assert(self.components.server_environment, "Component server_environment must be set")
		assert(self.components.service_urls, "Component service_urls must be set")

		local user_id = self.components.server_environment.user_id
		local password = self.components.server_environment.password
		local runtime_id = self.components.server_environment.runtime_id

		local request_body = {
			runtime_id = runtime_id,
			password = password
		}

		local response_handler = self:build_handler(callback)
		local request_url = self.components.service_urls.create_token
		self:request_server(request_url, request_body, response_handler, nil)
	end,
	ws_connect = function(self, callback)
		assert(self.components.server_environment, "Component server_environment must be set")
		assert(self.components.service_urls, "Component service_urls must be set")
		assert(self.components.tokens, "Component tokens must be set")

		local runtime_id = self.components.server_environment.runtime_id
		local ws_token = self.components.tokens.ws_token

		local connection_url = self.components.service_urls.connection .. "?runtime_id=" .. runtime_id .. "&ws_token=" .. ws_token
		local params = {
			protocol = "omgservers"
		}

		print(socket.gettime() .. " [OMGSERVER] Connect websocket, url=" .. connection_url)

		local ws_connection = websocket.connect(connection_url, params, function(_, _, data)
			if data.event == websocket.EVENT_DISCONNECTED then
				self:terminate_server(self.constants.WS_EXIT_CODE, "ws connection disconnected, message=" .. data.message)
			elseif data.event == websocket.EVENT_CONNECTED then
				print(socket.gettime() .. " [OMGSERVER] Websocket connected")
				if callback then
					callback()
				end
			elseif data.event == websocket.EVENT_ERROR then
				self:terminate_server(self.constants.WS_EXIT_CODE, "ws connection failed, message=" .. data.message)
			elseif data.event == websocket.EVENT_MESSAGE then
				local decoded_message = json.decode(data.message)
				local client_id = decoded_message.client_id
				local encoding = decoded_message.encoding
				local message
				if encoding == omgserver.constants.BASE64_ENCODED then
					message = crypt.decode_base64(decoded_message.message)
				elseif encoding == omgserver.constants.PLAIN_TEXT then
					message = decoded_message.message
				end
				
				self.components.server_state:add_server_event({
					qualifier = omgserver.constants.MESSAGE_RECEIVED,
					body = {
						client_id = client_id,
						message = message,
					},
				})
			end
		end)

		self.components:set_connection(ws_connection)
	end,
	ws_send = function(self, clients, encoding, message)
		assert(omgserver.components.connection, "Connection was not created")
		assert(type(message) == "string", "Message has to be string")

		local encoded_message = json.encode({
			clients = clients,
			encoding = encoding,
			message = message,
		})

		if self.settings.debug then
			print(socket.gettime() .. " [OMGSERVER] Outgoing message, encoded_message=" .. encoded_message)
		end

		websocket.send(omgserver.components.connection.ws_connection, encoded_message, {
			type = websocket.DATA_TYPE_TEXT
		})
	end,
	interchange = function(self, api_token, outgoing_commands, consumed_commands, callback)
		assert(self.components.server_environment, "Server environment must be set")
		assert(self.components.service_urls, "Service urls must be set")

		local request_body = {
			outgoing_commands = outgoing_commands,
			consumed_commands = consumed_commands
		}

		local response_handler = self:build_handler(callback)
		local request_url = self.components.service_urls.interchange
		self:request_server(request_url, request_body, response_handler, api_token)
	end,
	iterate = function(self, api_token)
		local server_state = self.components.server_state
		local outgoing_commands = server_state:pull_outgoing_commands()
		if #outgoing_commands > 0 then
			if self.settings.debug then
				print(socket.gettime() .. " [OMGSERVER] Outgoing commands, outgoing_commands=" .. json.encode(outgoing_commands))
			end
		end

		local consumed_commands = self.components.server_state:pull_consumed_commands()

		-- Switch between default and fast interchange intervals
		if #consumed_commands > 0 then
			server_state.iterations_in_vain = 0 
			server_state.fast_iterations = true
		else
			local iterations_in_vain = server_state.iterations_in_vain
			if iterations_in_vain >= self.settings.iterations_threshold then
				server_state.fast_iterations = false
			else
				server_state.iterations_in_vain = iterations_in_vain + 1
			end
		end

		self:interchange(api_token, outgoing_commands, consumed_commands, function(interchange_status, interchange_response)
			local incoming_commands = interchange_response.incoming_commands

			for _, incoming_command in ipairs(incoming_commands) do
				local command_id = incoming_command.id
				local command_qualifier = incoming_command.qualifier
				local command_body = incoming_command.body
				if self.settings.debug then
					print(socket.gettime() .. " [OMGSERVER] Handle command, id=" .. string.format("%.0f", command_id) .. ", qualifier=" .. command_qualifier .. ", body=" .. json.encode(command_body))
				end
				self.components.server_state:add_consumed_command(incoming_command)

				self.components.server_state:add_server_event({
					qualifier = omgserver.constants.COMMAND_RECEIVED,
					body = {
						command_qualifier = command_qualifier,
						command_body = command_body
					}
				})
			end
		end)
	end,
	update = function(self, dt)
		assert(self.components.event_handler, "Component event_handler must be set")

		if self.components.tokens then
			if self.components.server_state.waiting_for_response then
				-- 
			else
				local server_state = self.components.server_state
				local iterate_interval
				if server_state.fast_iterations then
					iterate_interval = self.settings.fast_interval
				else
					iterate_interval = self.settings.default_interval
				end
				server_state.iteration_timer = server_state.iteration_timer + dt
				if server_state.iteration_timer >= iterate_interval then
					server_state.iteration_timer = 0
					local api_token = self.components.tokens.api_token
					omgserver:iterate(api_token)
				end
			end
		end

		local handler = self.components.event_handler.handler
		local server_events = self.components.server_state:pull_server_events()
		for _, server_event in ipairs(server_events) do
			handler(server_event)
		end
	end,
	start = function(self, handler, debug, default_interval, fast_interval, iterations_threshold)
		self.settings.debug = debug or false
		self.settings.default_interval = default_interval or 1
		self.settings.fast_interval = fast_interval or self.settings.default_interval * 0.5
		assert(self.settings.default_interval > self.settings.fast_interval , "Fast interval should be less than default")
		self.settings.iterations_threshold = iterations_threshold or 4
		print(socket.gettime() .. " [OMGSERVER] Setting, debug=" .. tostring(self.settings.debug) .. ", default_interval=" .. self.settings.default_interval .. ", fast_interval=" .. self.settings.fast_interval)

		self.components:set_event_handler(handler)

		local service_url = os.getenv(omgserver.constants.SERVICE_URL)
		if not service_url then
			self:terminate_server(self.constants.ENVIRONMENT_EXIT_CODE, "environment variable is nil, variable=service_url")
		end

		local runtime_id = os.getenv(omgserver.constants.RUNTIME_ID)
		if not runtime_id then
			self:terminate_server(self.constants.ENVIRONMENT_EXIT_CODE, "environment variable is nil, variable=runtime_id")
		end
		
		local password = os.getenv(omgserver.constants.USER_PASSWORD)
		if not password then
			self:terminate_server(self.constants.ENVIRONMENT_EXIT_CODE, "environment variable is nil, variable=password")
		end

		local runtime_qualifier = os.getenv(omgserver.constants.RUNTIME_QUALIFIER)
		if not runtime_qualifier then
			self:terminate_server(self.constants.ENVIRONMENT_EXIT_CODE, "environment variable is nil, variable=runtime_qualifier")
		end

		print(socket.gettime() .. " [OMGSERVER] Environment, service_url=" .. service_url)
		print(socket.gettime() .. " [OMGSERVER] Environment, runtime_id=" .. runtime_id)
		print(socket.gettime() .. " [OMGSERVER] Environment, password=" .. string.sub(password, 1, 4) .. "..")
		print(socket.gettime() .. " [OMGSERVER] Environment, runtime_qualifier=" .. runtime_qualifier)

		self.components:set_server_environment(service_url, runtime_id, password, runtime_qualifier)
		self.components:set_service_urls(service_url)

		self:create_token(function(create_token_status, create_token_response)
			local api_token = create_token_response.api_token
			local ws_token = create_token_response.ws_token
			self.components:set_tokens(api_token, ws_token)

			self:ws_connect(function()
				self.components.server_state:add_server_event({
					qualifier = omgserver.constants.SERVER_STARTED,
					body = {
						runtime_qualifier = runtime_qualifier,
					}
				})
			end)
		end)
	end,
}

-- Entrypoint
return {
	constants = omgserver.constants,
	-- Service commands
	service_commands = {
		-- Methods
		set_attributes = function(service_commands, client_id, attributes)
			omgserver.components.server_state:add_outgoing_command({
				qualifier = omgserver.constants.SET_ATTRIBUTES,
				body = {
					client_id = client_id,
					attributes = {
						attributes = attributes,
					},
				},
			})
		end,
		set_profile = function(service_commands, client_id, profile)
			omgserver.components.server_state:add_outgoing_command({
				qualifier = omgserver.constants.SET_PROFILE,
				body = {
					client_id = client_id,
					profile = profile,
				},
			})
		end,
		respond_client = function(service_commands, client_id, message)
			assert(type(message) == "string", "Message has to be string")
			omgserver.components.server_state:add_outgoing_command({
				qualifier = omgserver.constants.RESPOND_CLIENT,
				body = {
					client_id = client_id,
					message = message,
				},
			})
		end,
		multicast_message = function(service_commands, clients, message)
			assert(type(message) == "string", "Message has to be string")
			omgserver.components.server_state:add_outgoing_command({
				qualifier = omgserver.constants.MULTICAST_MESSAGE,
				body = {
					clients = clients,
					message = message,
				},
			})
		end,
		broadcast_message = function(service_commands, message)
			assert(type(message) == "string", "Message has to be string")
			omgserver.components.server_state:add_outgoing_command({
				qualifier = omgserver.constants.BROADCAST_MESSAGE,
				body = {
					message = message,
				},
			})
		end,
		kick_client = function(service_commands, client_id)
			omgserver.components.server_state:add_outgoing_command({
				qualifier = omgserver.constants.KICK_CLIENT,
				body = {
					client_id = client_id,
				},
			})
		end,
		request_matchmaking = function(service_commands, client_id, mode)
			omgserver.components.server_state:add_outgoing_command({
				qualifier = omgserver.constants.REQUEST_MATCHMAKING,
				body = {
					client_id = client_id,
					mode = mode,
				},
			})
		end,
		stop_matchmaking = function(service_commands, reason)
			omgserver.components.server_state:add_outgoing_command({
				qualifier = omgserver.constants.STOP_MATCHMAKING,
				body = {
					reason = reason,
				},
			})
		end,
		upgrade_connection = function(service_commands, client_id)
			omgserver.components.server_state:add_outgoing_command({
				qualifier = omgserver.constants.UPGRADE_CONNECTION,
				body = {
					client_id = client_id,
					protocol = omgserver.constants.WEBSOCKET_PROTOCOL,
				},
			})
		end,
	},
	connections = {
		respond_text_message = function(connections, client_id, message)
			assert(omgserver.components.connection, "Connection was not created")
			omgserver:ws_send({ client_id }, omgserver.constants.PLAIN_TEXT, message)
		end,
		respond_binary_message = function(connections, client_id, message)
			assert(omgserver.components.connection, "Connection was not created")
			local encoded_message = crypt.encode_base64(message)
			omgserver:ws_send({ client_id }, omgserver.constants.BASE64_ENCODED, encoded_message)
		end,
		multicast_text_message = function(connections, clients, message)
			assert(omgserver.components.connection, "Connection was not created")
			omgserver:ws_send(clients, omgserver.constants.PLAIN_TEXT, message)
		end,
		multicast_binary_message = function(connections, clients, message)
			assert(omgserver.components.connection, "Connection was not created")
			local encoded_message = crypt.encode_base64(message)
			omgserver:ws_send(clients, omgserver.constants.BASE64_ENCODED, encoded_message)
		end,
		broadcast_text_message = function(connections, message)
			assert(omgserver.components.connection, "Connection was not created")
			omgserver:ws_send(nil, omgserver.constants.PLAIN_TEXT, message)
		end,
		broadcast_binary_message = function(connections, message)
			assert(omgserver.components.connection, "Connection was not created")
			local encoded_message = crypt.encode_base64(message)
			omgserver:ws_send(nil, omgserver.constants.BASE64_ENCODED, encoded_message)
		end,
	},
	-- Methods
	start = function(self, handler, debug, default_interval, fast_interval, iterations_threshold)
		omgserver:start(handler, debug, default_interval, fast_interval, iterations_threshold)
	end,
	get_qualifier = function(self)
		assert(omgserver.components.server_environment, "Server was not initialized")
		return omgserver.components.server_environment.runtime_qualifier
	end,
	update = function(self, dt)
		omgserver:update(dt)
	end,
}