local screens_messages
screens_messages = {
	constants = {
		SET_STATE = "set_state",
		APPLY_EVENTS = "apply_events",
		REQUEST_MOVE = "request_move",
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
	create_request_move_message = function(self)
		return {
		}
	end,
}

return screens_messages