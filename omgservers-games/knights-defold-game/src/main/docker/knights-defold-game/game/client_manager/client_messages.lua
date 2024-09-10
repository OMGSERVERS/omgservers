local client_messages
client_messages = {
	constants = {
		CLIENT_EVENT_RECEIVED = "client_event_received",
		SIGNING_UP_REQUESTED = "signing_up_requested",
		SIGNING_IN_REQUESTED = "signing_in_requested",
		SENDING_REQUESTED = "sending_requested",
		RECONNECTING_REQUESTED = "reconnecting_requested",
	},
	-- Methods
	create_client_event_received_message = function(self, event_qualifier, event_body)
		return {
			event_qualifier = event_qualifier,
			event_body = event_body,
		}
	end,
	create_signing_up_requested_message = function(self)
		return {
		}
	end,
	create_signing_in_requested_message = function(self, user_id, password)
		return {
			user_id = user_id,
			password = password,
		}
	end,
	create_sending_requested_message = function(self, message)
		return message
	end,
	create_reconnecting_requested_message = function(self)
		return {
		}
	end,
}

return client_messages