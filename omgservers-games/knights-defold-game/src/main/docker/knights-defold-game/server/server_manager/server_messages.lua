local server_messages
server_messages = {
	constants = {
		SERVER_EVENT_RECEIVED = "server_event_received",
	},
	-- Methods
	create_server_event_received_message = function(self, event_qualifier, event_body)
		return {
			event_qualifier = event_qualifier,
			event_body = event_body,
		}
	end,
}

return server_messages