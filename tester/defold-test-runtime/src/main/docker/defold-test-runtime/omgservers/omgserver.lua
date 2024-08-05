local omgservers
omgservers = {
	constants = {
		ENVIRONMENT_EXIT_CODE = 1,
		TOKEN_EXIT_CODE = 2,
		CONFIG_EXIT_CODE = 3,
		API_EXIT_CODE = 4
	},
	settings = {
		debug = true,
		websockets = true,
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
				-- methods
				add_outgoing_command = function(server_state, command)
					server_state.outgoing_commands[#server_state.outgoing_commands + 1] = command
				end,
				add_consumed_command = function(server_state, command)
					assert(command.id, "Consumed command must have id")
					server_state.consumed_commands[#server_state.consumed_commands + 1] = command.id
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
	ws_connect = function(self)
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
	handle_command = function(self, command_handler, command_qualifier, command_body)
		assert(self.components.server_state, "Server state must be created")

		local outgoing_commands = command_handler(command_qualifier, command_body)
		if outgoing_commands and #outgoing_commands > 0 then
			if self.settings.debug then
				print("[OMGSERVER] Outgoing commands")
				pprint(outgoing_commands)
			end
			for command_index, outgoing_command in ipairs(outgoing_commands) do
				self.components.server_state:add_outgoing_command(outgoing_command)
			end
		end
	end,
	iterate = function(self, api_token, command_handler)
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
					self:handle_command(command_handler, command_qualifier, command_body)

					self.components.server_state:add_consumed_command(incoming_command)
				end

				self:handle_command(command_handler, "UPDATE_RUNTIME", {})
			end
		end)
	end,
	update = function(self, dt, command_handler)
		assert(self.components.server_state, "Server state must be created")
		
		if self.components.tokens then
			if self.components.server_state.exchanging then
				-- 
			else
				local api_token = self.components.tokens.api_token
				local internal_state = self.components.internal_state

				internal_state.iterate_timer = internal_state.iterate_timer + dt
				if internal_state.iterate_timer >= internal_state.iterate_interval then
					internal_state.iterate_timer = internal_state.iterate_timer - internal_state.iterate_interval
					omgservers:iterate(api_token, command_handler)
				end
			end
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
						if callback then
							callback(version_config)
						end
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
		assert(omgservers.components.server_environment, "Init server first")
		return omgservers.components.server_environment.runtime_qualifier
	end,
	get_version = function(self)
		assert(omgservers.components.config, "Init server first")
		return omgservers.components.config.version_config
	end,
	update = function(self, dt, command_handler)
		omgservers:update(dt, command_handler)
	end,
}