local core_state
core_state = {
	IN_AUTH = "in_auth",
	GETTING_PROFILE = "getting_profile",
	IN_LOBBY = "in_lobby",
	GETTING_STATE = "getting_state",
	IN_MATCH = "in_match",
	GAME_FAILED = "game_failed",
	IN_WAIT = "in_wait",
	game_state = nil,
	client_id = nil,
	profile = nil,
	-- Methods
	change_game_state = function(self, next_state)
		local current_state = self.game_state
		self.game_state = next_state
		print(socket.gettime() .. " [CORE_STATE] Changed state, " .. tostring(current_state) .. "->" .. tostring(next_state))
	end,
	set_client_id = function(self, client_id)
		self.client_id = client_id
		print(socket.gettime() .. " [CORE_STATE] Client was set, client_id=" .. client_id)
	end,
	set_profile = function(self, profile)
		self.profile = profile
		print(socket.gettime() .. " [CORE_STATE] Profile was set")
	end,
}

return core_state