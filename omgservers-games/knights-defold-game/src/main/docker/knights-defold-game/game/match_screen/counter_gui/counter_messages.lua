local counter_messages
counter_messages = {
	constants = {
		RESET_COUNTER = "reset_counter",
		HIDE_COUNTER = "hide_counter",
	},
	-- Methods
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

return counter_messages