local joining_screen
joining_screen = {
	SET_STATE = "set_state",
	-- Methods
	set_state = function(self, receiver, state_text)
		msg.post(receiver, joining_screen.SET_STATE, {
			state_text = state_text,
		})
	end,
}

return joining_screen