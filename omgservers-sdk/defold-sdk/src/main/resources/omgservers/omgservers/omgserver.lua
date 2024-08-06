local omgservers
omgservers = {
	constants = {
		ENVIRONMENT_EXIT_CODE = 1,
		TOKEN_EXIT_CODE = 2,
		CONFIG_EXIT_CODE = 3,
		API_EXIT_CODE = 4,
		WS_EXIT_CODE = 5,
	},
	settings = {
		debug = true,
	},
	components = {
		set_server_environment = function(components, service_url, user_id, password, runtime_id, runtime_qualifier)
			components.server_environment = {
				service_url = service_url,
				user_id = user_id,
				password = password,
				runtime_id = runtime_id,
				runtime_qualifier = runtime_qualifier,
			}
		end,
		set_service_urls = function(components, service_url)
			components.service_urls = {
				create_token = service_url .. "/omgservers/v1/entrypoint/worker/request/create-token",
				get_config = service_url .. "/omgservers/v1/entrypoint/worker/request/get-config",
				interchange = service_url .. "/omgservers/v1/entrypoint/worker/request/interchange",
				connection = service_url .. "/omgservers/v1/entrypoint/websocket/connection",
			}
		end,
		set_server_state = function(components)
			components.server_state = {
				exchanging = false,
				outgoing_commands = {},
				consumed_commands = {},
				server_events = {},
				-- methods
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
			}
		end,
		set_tokens = function(components, api_token, ws_token)
			components.tokens = {
				api_token = api_token,
				ws_token = ws_token,
			}
		end,
		set_internal_state = function(components, iterate_interval)
			components.internal_state = {
				iterate_interval = iterate_interval,
				iterate_timer = 0,
			}
		end,
		set_config = function(components, version_config)
			components.config = {
				version_config = version_config,
			}
		end,
		set_connection = function(components, ws_connection)
			components.connection = {
				ws_connection = ws_connection,
			}
		end,
	},
	terminate_server = function(self, code, reason)
		print("[OMGSERVER] Terminated, code=" .. code .. ", reason=" .. reason)
		os.exit(code)
	end,
	build_handler = function(self, callback)
		return function(_, id, response)
			self.components.server_state.exchanging = false

			local response_status = response.status
			local response_body = response.response

			if response_status >= 300 then
				self:terminate_server(self.constants.API_EXIT_CODE, "api request failed, status=" .. response_status .. ", body=" .. response_body)
			end

			if self.settings.debug then
				print("[OMGSERVER] Response, status=" .. response_status .. ", body=" .. response_body)
			end
			
			local decoded_response = json.decode(response_body)
			if callback then
				callback(response_status, decoded_response)
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

		if self.settings.debug then
			print("[OMGSERVER] Request, " .. method .. " " .. url .. ", body=" .. encoded_body)
		end

		self.components.server_state.exchanging = true
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
			user_id = user_id,
			password = password
		}
		
		local response_handler = self:build_handler(callback)
		local request_url = self.components.service_urls.create_token
		self:request_server(request_url, request_body, response_handler, nil)
	end,
	get_config = function(self, api_token, callback)
		assert(self.components.server_environment, "Component server_environment must be set")
		assert(self.components.service_urls, "Component service_urls must be set")

		local runtime_id = self.components.server_environment.runtime_id

		local request_body = {
			runtime_id = runtime_id
		}

		local response_handler = self:build_handler(callback)
		local request_url = self.components.service_urls.get_config
		self:request_server(request_url, request_body, response_handler, api_token)
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

		print("[OMGSERVER] Connect websocket, url=" .. connection_url)

		local ws_connection = websocket.connect(connection_url, params, function(_, _, data)
			if data.event == websocket.EVENT_DISCONNECTED then
				self:terminate_server(self.constants.WS_EXIT_CODE, "ws connection disconnected, message=" .. data.message)
			elseif data.event == websocket.EVENT_CONNECTED then
				print("[OMGSERVER] Websocket connected")
				if callback then
					callback()
				end
			elseif data.event == websocket.EVENT_ERROR then
				self:terminate_server(self.constants.WS_EXIT_CODE, "ws connection failed, message=" .. data.message)
			elseif data.event == websocket.EVENT_MESSAGE then
				self.components.server_state:add_server_event({
					qualifier = "WS_MESSAGE_RECEIVED",
					message = data.message
				})
			end
		end)

		self.components:set_connection(ws_connection)
	end,
	interchange = function(self, api_token, outgoing_commands, consumed_commands, callback)
		assert(self.components.server_environment, "Server environment must be set")
		assert(self.components.service_urls, "Service urls must be set")

		local runtime_id = self.components.server_environment.runtime_id

		local request_body = {
			runtime_id = runtime_id,
			outgoing_commands = outgoing_commands,
			consumed_commands = consumed_commands
		}

		local response_handler = self:build_handler(callback)
		local request_url = self.components.service_urls.interchange
		self:request_server(request_url, request_body, response_handler, api_token)
	end,
	iterate = function(self, api_token)
		assert(self.components.server_state, "Server state must be created")
		
		local outgoing_commands = self.components.server_state:pull_outgoing_commands()
		local consumed_commands = self.components.server_state:pull_consumed_commands()

		self:interchange(api_token, outgoing_commands, consumed_commands, function(response_status, decoded_response)
			if response_status == 200 then
				local incoming_commands = decoded_response.incoming_commands

				for _, incoming_command in ipairs(incoming_commands) do
					local command_id = incoming_command.id
					local command_qualifier = incoming_command.qualifier
					local command_body = incoming_command.body
					if self.settings.debug then
						print("[OMGSERVER] Handle command, id=" .. string.format("%.0f", command_id) .. ", qualifier=" .. command_qualifier)
						pprint(command_body)
					end
					self.components.server_state:add_consumed_command(incoming_command)

					self.components.server_state:add_server_event({
						qualifier = "COMMAND_RECEIVED",
						command = {
							qualifier = command_qualifier,
							body = command_body
						}
					})
				end
			end
		end)
	end,
	handle = function(self, handler)
		assert(self.components.server_state, "Server state must be created")

		local server_events = self.components.server_state:pull_server_events()
		for _, server_event in ipairs(server_events) do
			handler(server_event)
		end
	end,
	update = function(self, dt, handler)
		assert(self.components.server_state, "Server state must be created")
		
		if self.components.tokens then
			if self.components.server_state.exchanging then
				-- 
			else
				local internal_state = self.components.internal_state
				internal_state.iterate_timer = internal_state.iterate_timer + dt
				if internal_state.iterate_timer >= internal_state.iterate_interval then
					internal_state.iterate_timer = internal_state.iterate_timer - internal_state.iterate_interval
					local api_token = self.components.tokens.api_token
					omgservers:iterate(api_token)
				end
			end

			self:handle(handler)
		end
	end,
	init = function(self, callback)
		local service_url = os.getenv("OMGSERVERS_URL")
		if not service_url then
			self:terminate_server(self.constants.ENVIRONMENT_EXIT_CODE, "environment variable is nil, variable=service_url")
		end

		local user_id = os.getenv("OMGSERVERS_USER_ID")
		if not user_id then
			self:terminate_server(self.constants.ENVIRONMENT_EXIT_CODE, "environment variable is nil, variable=user_id")
		end

		local password = os.getenv("OMGSERVERS_PASSWORD")
		if not password then
			self:terminate_server(self.constants.ENVIRONMENT_EXIT_CODE, "environment variable is nil, variable=password")
		end

		local runtime_id = os.getenv("OMGSERVERS_RUNTIME_ID")
		if not runtime_id then
			self:terminate_server(self.constants.ENVIRONMENT_EXIT_CODE, "environment variable is nil, variable=runtime_id")
		end

		local runtime_qualifier = os.getenv("OMGSERVERS_RUNTIME_QUALIFIER")
		if not runtime_qualifier then
			self:terminate_server(self.constants.ENVIRONMENT_EXIT_CODE, "environment variable is nil, variable=runtime_qualifier")
		end

		print("[OMGSERVER] Environment, service_url=" .. service_url)
		print("[OMGSERVER] Environment, user_id=" .. user_id)
		print("[OMGSERVER] Environment, password=" .. string.sub(password, 1, 4) .. "..")
		print("[OMGSERVER] Environment, runtime_id=" .. runtime_id)
		print("[OMGSERVER] Environment, runtime_qualifier=" .. runtime_qualifier)

		self.components:set_server_environment(service_url, user_id, password, runtime_id, runtime_qualifier)
		self.components:set_service_urls(service_url)
		self.components:set_server_state()

		self:create_token(function(response_status, decoded_response)
			if response_status == 200 then
				local api_token = decoded_response.api_token
				local ws_token = decoded_response.ws_token
				self.components:set_tokens(api_token, ws_token)
				self.components:set_internal_state(1)

				self:get_config(api_token, function(response_status, decoded_response)
					if response_status == 200 then
						local version_config = decoded_response.version_config
						self.components:set_config(version_config)

						self:ws_connect(function()
							if callback then
								callback(runtime_qualifier, version_config)
							end
						end)
					else
						self:terminate_server(self.constants.CONFIG_EXIT_CODE, "version config was not got")
					end
				end)
			else
				self:terminate_server(self.constants.TOKEN_EXIT_CODE, "token was not created")
			end
		end)
	end,
}

return {
	init = function(self, callback)
		omgservers:init(callback)
	end,
	get_qualifier = function(self)
		assert(omgservers.components.server_environment, "Server was not initialized")
		return omgservers.components.server_environment.runtime_qualifier
	end,
	get_config = function(self)
		assert(omgservers.components.config, "Server was not initialized")
		return omgservers.components.config.version_config
	end,
	send_service_command = function(self, command)
		assert(omgservers.components.server_state, "Server was not initialized")
		omgservers.components.server_state:add_outgoing_command(command)
	end,
	send_text_message = function(self, message)
		assert(omgservers.components.connection, "Connection was not created")
		
		websocket.send(omgservers.components.connection.ws_connection, message, {
			type = websocket.DATA_TYPE_TEXT
		})
	end,
	send_binary_message = function(self, message)
		assert(omgservers.components.connection, "Connection was not created")

		websocket.send(omgservers.components.connection.ws_connection, message, {
			type = websocket.DATA_TYPE_BINARY
		})
	end,
	update = function(self, dt, handler)
		omgservers:update(dt, handler)
	end,
}