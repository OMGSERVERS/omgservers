local game_messages
game_messages = {
	constants = {
		PLAYER_CREATED = "player_created",
		PLAYER_SPAWNED = "player_spawned",
		PLAYER_KILLED = "player_killed",
		PLAYER_MOVED = "player_moved",
		PLAYER_DELETED = "player_deleted",
	},
	-- Methods
	create_player_created_event = function(self, client_id, nickname)
		return {
			qualifier = game_messages.constants.PLAYER_CREATED,
			client_id = client_id,
			nickname = nickname
		}
	end,
	create_player_spawned_event = function(self, client_id, in_attack, x, y)
		return {
			qualifier = game_messages.constants.PLAYER_SPAWNED,
			client_id = client_id,
			in_attack = in_attack,
			x = x,
			y = y,
		}
	end,
	create_player_killed_event = function(self, killed_client_id, by_client_id)
		return {
			qualifier = game_messages.constants.PLAYER_KILLED,
			killed_client_id = killed_client_id,
			by_client_id = by_client_id,
		}
	end,
	create_player_moved_event = function(self, client_id, x, y)
		return {
			qualifier = game_messages.constants.PLAYER_MOVED,
			client_id = client_id,
			x = x,
			y = y,
		}
	end,
	create_player_deleted_event = function(self, client_id)
		return {
			qualifier = game_messages.constants.PLAYER_DELETED,
			client_id = client_id,
		}
	end,
}

return game_messages