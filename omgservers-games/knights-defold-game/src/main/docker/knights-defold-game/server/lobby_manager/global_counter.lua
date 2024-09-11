local global_counter
global_counter = {
	value = 0,
	-- Methods
	get_next_id = function(self)
		self.value = self.value + 1
		return self.value
	end,
}

return global_counter