local client_messages
client_messages = {
	constants = {
		SIGN_UP = "sign_up",
		SIGN_IN = "sign_in",
		SEND_COMMAND = "send_command",
		SEND_MESSAGE = "send_message",
		RECONNECT = "reconnect",
	},
	-- Methods
	create_sign_up_message = function(self)
		return {
		}
	end,
	create_sign_in_message = function(self, user_id, password)
		return {
			user_id = user_id,
			password = password,
		}
	end,
	create_send_command_message = function(self, message)
		return {
			message = message,
		}
	end,
	create_send_message_message = function(self, message)
		return {
			message = message,
		}
	end,
	create_reconnect_message = function(self)
		return {
		}
	end,
}

return client_messages