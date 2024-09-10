local server_messages
server_messages = {
	constants = {
		SET_PROFILE = "set_profile",
		SET_STATE = "set_state",
		APPLY_EVENTS = "apply_events",
	},
	-- Methods
	create_set_profile_message = function(self, profile)
		return {
			qualifier = server_messages.constants.SET_PROFILE,
			profile = profile,
		}
	end,
	create_set_state_message = function(self, dangling_players, spawned_players)
		return {
			qualifier = server_messages.constants.SET_PROFILE,
			dangling_players = dangling_players,
			spawned_players = spawned_players,
		}
	end,
	create_apply_events_message = function(self, events)
		return {
			qualifier = server_messages.constants.APPLY_EVENTS,
			events = events,
		}
	end,
}

return server_messages