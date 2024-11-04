local match_screen
match_screen = {
	SET_STATE = "set_state",
	APPLY_EVENTS = "apply_events",
	POINTED = "pointed",
	-- Methods
	set_state = function(self, recipient, settings, dangling_players, spawned_players)
		msg.post(recipient, match_screen.SET_STATE, {
			settings = settings,
			dangling_players = dangling_players,
			spawned_players = spawned_players,
		})
	end,
	apply_events = function(self, recipient, events)
		msg.post(recipient, match_screen.APPLY_EVENTS, {
			events = events,
		})
	end,
	pointed = function(self, recipient, x, y)
		msg.post(recipient, match_screen.POINTED, {
			x = x,
			y = y,
		})
	end,
}

return match_screen