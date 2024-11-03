local leave_screen
leave_screen = {
	SET_STATE = "set_state",
	-- Methods
	set_state = function(self, receiver, state_text)
		msg.post(receiver, leave_screen.SET_STATE, {
			state_text = state_text,
		})
	end,
}

return leave_screen