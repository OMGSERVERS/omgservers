local screens_messages
screens_messages = {
	constants = {
		SET_STATE = "set_state",
		MOVE_TO = "move_to",
		GO_DEATH = "go_death",
	},
	-- Methods
	create_set_state_message = function(self, controllable, nickname, in_attack)
		return {
			controllable = controllable,
			nickname = nickname,
			in_attack = in_attack
		}
	end,
	create_move_to_message = function(self, x, y)
		return {
			x = x,
			y = y,
		}
	end,
	create_go_death_message = function(self)
		return {
		}
	end,
}

return screens_messages