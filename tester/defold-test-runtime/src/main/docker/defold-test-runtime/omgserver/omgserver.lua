local omgservers
omgservers = {
	components = {
		set_environment = function(self, service_url, user_id, password, runtime_id, runtime_qualifier)
			self.environment = {
				service_url = service_url,
				user_id = user_id,
				password = password,
				runtime_id = runtime_id,
				runtime_qualifier = runtime_qualifier,
			}
		end,
		set_server_urls = function(self, service_url)
			self.server_urls = {
				create_token = service_url .. "/omgservers/v1/entrypoint/worker/request/create-token",
				get_version = service_url .. "/omgservers/v1/entrypoint/worker/request/get-version",
				interchange = service_url .. "/omgservers/v1/entrypoint/worker/request/interchange",
			}
		end,
		set_server_state = function(self)
			self.server_state = {
				outgoing_commands = {},
				consumed_commands = {},
				-- methods
				add_outgoing_command = function(self, command)
					self.outgoing_commands[#self.outgoing_commands + 1] = command
				end,
				add_consumed_command = function(self, command)
					assert(command.id, "Consumed command must have id")
					self.consumed_commands[#self.consumed_commands + 1] = command.id
				end,
				pull_outgoing_commands = function(self)
					local outgoing_commands = self.outgoing_commands
					self.outgoing_commands = {}
					return outgoing_commands
				end,
				pull_consumed_commands = function(self)
					local consumed_commands = self.consumed_commands
					self.consumed_commands = {}
					return consumed_commands
				end,
			}
		end
	},
	settings = {
		debug = true,
	},
	bootstrap = function(self)
		local service_url = os.getenv("OMGSERVERS_URL")
		if not service_url then
			error("service_url is nil")
		end

		local user_id = os.getenv("OMGSERVERS_USER_ID")
		if not user_id then
			error("user_id is nil")
		end

		local password = os.getenv("OMGSERVERS_PASSWORD")
		if not password then
			error("password is nil")
		end

		local runtime_id = os.getenv("OMGSERVERS_RUNTIME_ID")
		if not runtime_id then
			error("runtime_id is nil")
		end

		local runtime_qualifier = os.getenv("OMGSERVERS_RUNTIME_QUALIFIER")
		if not runtime_qualifier then
			error("runtime_qualifier is nil")
		end

		print("[OMGSERVERS] Environment, service_url=" .. service_url)
		print("[OMGSERVERS] Environment, user_id=" .. user_id)
		print("[OMGSERVERS] Environment, password=" .. string.sub(password, 1, 4) .. "..")
		print("[OMGSERVERS] Environment, runtime_id=" .. runtime_id)
		print("[OMGSERVERS] Environment, runtime_qualifier=" .. runtime_qualifier)
		
		self.components:set_environment(service_url, user_id, password, runtime_id, runtime_qualifier)
		self.components:set_server_urls(service_url)
	end
}

return {
	bootstrap = function(self)
		omgservers:bootstrap()
	end
}