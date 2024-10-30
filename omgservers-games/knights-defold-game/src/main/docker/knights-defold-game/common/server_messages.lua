local server_messages
server_messages = {
	SET_PROFILE = "set_profile",
	SET_STATE = "set_state",
	REQUEST_MOVE = "request_move",
	APPLY_EVENTS = "apply_events",
	-- Methods
	set_profile = function(self, profile)
		return {
			qualifier = server_messages.SET_PROFILE,
			profile = profile,
		}
	end,
	set_state = function(self, settings, dangling_players, spawned_players)
		return {
			qualifier = server_messages.SET_STATE,
			settings = settings,
			dangling_players = dangling_players,
			spawned_players = spawned_players,
		}
	end,
	request_move = function(self)
		return {
			qualifier = server_messages.REQUEST_MOVE,
		}
	end,
	apply_events = function(self, events)
		return {
			qualifier = server_messages.APPLY_EVENTS,
			events = events,
		}
	end,
}

return server_messages