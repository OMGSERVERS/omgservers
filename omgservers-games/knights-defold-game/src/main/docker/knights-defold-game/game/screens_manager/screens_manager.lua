local screens_manager
screens_manager = {
	CREATE_AUTH_SCREEN = "create_auth_screen",
	CREATE_LOBBY_SCREEN = "create_lobby_screen",
	CREATE_MATCH_SCREEN = "create_match_screen",
	CREATE_LEAVE_SCREEN = "create_leave_screen",
	-- Methods
	create_auth_screen = function(self, receiver)
		msg.post(receiver, screens_manager.CREATE_AUTH_SCREEN, {
		})
	end,
	create_lobby_screen = function(self, receiver)
		msg.post(receiver, screens_manager.CREATE_LOBBY_SCREEN, {
		})
	end,
	create_match_screen = function(self, receiver, settings, dangling_players, spawned_players)
		msg.post(receiver, screens_manager.CREATE_MATCH_SCREEN, {
			settings = settings,
			dangling_players = dangling_players,
			spawned_players = spawned_players,
		})
	end,
	create_leave_screen = function(self, receiver)
		msg.post(receiver, screens_manager.CREATE_LEAVE_SCREEN, {
		})
	end,
}

return screens_manager