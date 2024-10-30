local wait_screen
wait_screen = {
	SET_STATE = "set_state",
	-- Methods
	set_state = function(self, receiver, state_text)
		msg.post(receiver, wait_screen.SET_STATE, {
			state_text = state_text,
		})
	end,
}

return wait_screen