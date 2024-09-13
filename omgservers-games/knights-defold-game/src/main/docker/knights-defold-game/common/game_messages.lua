local game_messages
game_messages = {
	constants = {
		REQUEST_PROFILE = "request_profile",
		REQUEST_MATCHMAKING = "request_matchmaking",
		REQUEST_STATE = "request_state",
		REQUEST_SPAWN = "request_spawn",
		MOVE_PLAYER = "move_player",
	},
	-- Methods
	create_request_profile_message = function(self)
		return {
			qualifier = game_messages.constants.REQUEST_PROFILE,
		}
	end,
	create_request_matchmaking_message = function(self)
		return {
			qualifier = game_messages.constants.REQUEST_MATCHMAKING,
		}
	end,
	create_request_state_message = function(self)
		return {
			qualifier = game_messages.constants.REQUEST_STATE,
		}
	end,
	create_request_spawn_message = function(self)
		return {
			qualifier = game_messages.constants.REQUEST_SPAWN,
		}
	end,
	create_move_player_message = function(self, x, y)
		return {
			qualifier = game_messages.constants.MOVE_PLAYER,
			x = x,
			y = y,
		}
	end,
}

return game_messages