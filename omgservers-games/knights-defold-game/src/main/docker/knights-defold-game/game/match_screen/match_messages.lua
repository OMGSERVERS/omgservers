local screens_messages
screens_messages = {
	constants = {
		SET_STATE = "set_state",
		APPLY_EVENTS = "apply_events",
		REQUEST_MOVE = "request_move",
		RESET_COUNTER = "reset_counter",
		HIDE_COUNTER = "hide_counter",
	},
	-- Methods
	create_set_state_message = function(self, settings, dangling_players, spawned_players)
		return {
			settings = settings,
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
	create_reset_counter_message = function(self, value)
		return {
			value = value,
		}
	end,
	create_hide_counter_message = function(self)
		return {
		}
	end,
}

return screens_messages