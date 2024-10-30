local events_container
events_container = {
	events = {},
	-- Methods
	add_event = function(self, event)
		self.events[#self.events + 1] = event
	end,
	pull_events = function(self)
		local events = self.events
		self.events = {}
		return events
	end,
}

return events_container