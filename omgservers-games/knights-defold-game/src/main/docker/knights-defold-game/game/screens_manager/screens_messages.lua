local screens_messages
screens_messages = {
	constants = {
		AUTH_SCREEN_REQUESTED = "auth_screen_requested",
		LOBBY_SCREEN_REQUESTED = "lobby_screen_requested",
		MATCH_SCREEN_REQUESTED = "match_screen_requested",
	},
	-- Methods
	create_auth_screen_requested_message = function(self)
		return {
		}
	end,
	create_lobby_screen_requested_message = function(self)
		return {
		}
	end,
	create_match_screen_requested_message = function(self, settings, dangling_players, spawned_players)
		return {
			settings = settings,
			dangling_players = dangling_players,
			spawned_players = spawned_players,
		}
	end,
}

return screens_messages