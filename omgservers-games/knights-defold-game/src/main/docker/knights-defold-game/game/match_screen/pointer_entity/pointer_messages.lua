local screens_messages
screens_messages = {
	constants = {
		ENABLE_POINTER = "enable_pointer",
		DISABLE_POINTER = "disable_pointer",
		SWITCH_POINTER = "switch_pointer",
	},
	-- Methods
	create_enable_pointer_message = function(self, x, y)
		return {
			x = x,
			y = y,
		}
	end,
	create_disable_pointer_message = function(self)
		return {
		}
	end,
	create_switch_pointer_message = function(self, x, y)
		return {
			x = x,
			y = y,
		}
	end,
}

return screens_messages