local lobby_screen
lobby_screen = {
	SET_PROFILE = "set_profile",
	-- Methods
	set_profile = function(self, recipient, profile)
		msg.post(recipient, lobby_screen.SET_PROFILE, profile)
	end,
}

return lobby_screen