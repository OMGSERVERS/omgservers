local client_manager
client_manager = {
	SIGN_UP = "sign_up",
	SIGN_IN = "sign_in",
	SEND_COMMAND = "send_command",
	SEND_MESSAGE = "send_message",
	RECONNECT = "reconnect",
	-- Methods
	sign_up = function(self, receiver)
		msg.post(receiver, client_manager.SIGN_UP, {
		})
	end,
	sign_in = function(self, receiver, user_id, password)
		msg.post(receiver, client_manager.SIGN_IN, {
			user_id = user_id,
			password = password,
		})
	end,
	send_command = function(self, receiver, message)
		msg.post(receiver, client_manager.SEND_COMMAND, {
			message = message,
		})
	end,
	send_message = function(self, receiver, message)
		msg.post(receiver, client_manager.SEND_MESSAGE, {
			message = message,
		})
	end,
	reconnect = function(self, receiver)
		msg.post(receiver, client_manager.RECONNECT, {
		})
	end,
}

return client_manager