local auth_messages
auth_messages = {
	constants = {
		SET_STATE_REQUESTED = "set_state_requested",
	},
	-- Methods
	create_set_state_requested_message = function(self, state_text)
		return {
			state_text = state_text,
		}
	end,
}

return auth_messages