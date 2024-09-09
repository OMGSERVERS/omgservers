local runtime_messages
runtime_messages = {
	constants = {
		RUNTIME_INITIALIZED = "runtime_initialized",
		COMMAND_RECEIVED = "command_received",
		MESSAGE_RECEIVED = "message_received",
	},
	-- Methods
	create_runtime_initialized_message = function(self, version_config)
		return {
			version_config = version_config,
		}
	end,
	create_command_received_message = function(self, command_qualifier, command_body)
		return {
			command_qualifier = command_qualifier,
			command_body = command_body,
		}
	end,
	create_message_received_message = function(self, client_id, message)
		return {
			client_id = client_id,
			message = message,
		}
	end,
}

return runtime_messages