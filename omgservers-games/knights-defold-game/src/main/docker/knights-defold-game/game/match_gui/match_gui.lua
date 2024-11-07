local match_gui
match_gui = {
	ENABLE_SPAWN_COUNTDOWN = "enable_spawn_countdown",
	DISABLE_SPAWN_COUNTDOWN = "disable_spawn_countdown",
	-- Methods
	enable_spawn_countdown = function(self, recipient, time_to_spawn)
		msg.post(recipient, match_gui.ENABLE_SPAWN_COUNTDOWN, {
			time_to_spawn = time_to_spawn,
		})
	end,
	disable_spawn_countdown = function(self, recipient)
		msg.post(recipient, match_gui.DISABLE_SPAWN_COUNTDOWN, {
		})
	end,
}

return match_gui