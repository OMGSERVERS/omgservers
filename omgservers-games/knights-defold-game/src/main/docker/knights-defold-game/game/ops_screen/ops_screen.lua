local ops_screen
ops_screen = {
	SET_STATE = "set_state",
	-- Methods
	set_state = function(self, receiver, state_text)
		msg.post(receiver, ops_screen.SET_STATE, {
			state_text = state_text,
		})
	end,
}

return ops_screen