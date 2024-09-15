local screens_messages
screens_messages = {
	constants = {
		SET_STATE = "set_state",
		APPLY_EVENTS = "apply_events",
	},
	-- Methods
	create_set_state_message = function(self, dangling_players, spawned_players)
		return {
			dangling_players = dangling_players,
			spawned_players = spawned_players,
		}
	end,
	create_apply_events_message = function(self, events)
		return {
			events = events,
		}
	end,
}

return screens_messages