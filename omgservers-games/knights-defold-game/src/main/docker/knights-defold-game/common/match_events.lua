local match_events
match_events = {
	PLAYER_CREATED = "player_created",
	PLAYER_SPAWNED = "player_spawned",
	PLAYER_KILLED = "player_killed",
	PLAYER_MOVED = "player_moved",
	PLAYER_DELETED = "player_deleted",
	-- Methods
	player_created = function(self, client_id, nickname)
		return {
			qualifier = match_events.PLAYER_CREATED,
			client_id = client_id,
			nickname = nickname
		}
	end,
	player_spawned = function(self, client_id, x, y)
		return {
			qualifier = match_events.PLAYER_SPAWNED,
			client_id = client_id,
			x = x,
			y = y,
		}
	end,
	player_killed = function(self, killed_client_id, by_client_id)
		return {
			qualifier = match_events.PLAYER_KILLED,
			killed_client_id = killed_client_id,
			by_client_id = by_client_id,
		}
	end,
	player_moved = function(self, client_id, x, y)
		return {
			qualifier = match_events.PLAYER_MOVED,
			client_id = client_id,
			x = x,
			y = y,
		}
	end,
	player_deleted = function(self, client_id)
		return {
			qualifier = match_events.PLAYER_DELETED,
			client_id = client_id,
		}
	end,
}

return match_events