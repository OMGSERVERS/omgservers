local match_player
match_player = {
	SET_STATE = "set_state",
	MOVE_TO = "move_to",
	GO_DEATH = "go_death",
	-- Methods
	set_state = function(self, recipient, controllable, nickname)
		msg.post(recipient, match_player.SET_STATE, {
			controllable = controllable,
			nickname = nickname,
		})
	end,
	move_to = function(self, recipient, x, y)
		msg.post(recipient, match_player.MOVE_TO, {
			x = x,
			y = y,
		})
	end,
	go_death = function(self, recipient)
		msg.post(recipient, match_player.GO_DEATH, {
		})
	end,
}

return match_player