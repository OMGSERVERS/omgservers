local match_gui
match_gui = {
	RESET_COUNTER = "reset_counter",
	HIDE_COUNTER = "hide_counter",
	-- Methods
	reset_counter = function(self, recipient, value)
		msg.post(recipient, match_gui.RESET_COUNTER, {
			value = value,
		})
	end,
	hide_counter = function(self, recipient)
		msg.post(recipient, match_gui.HIDE_COUNTER, {
		})
	end,
}

return match_gui