local player_messages
player_messages = {
	constants = {
		REQUEST_PROFILE = "request_profile",
	},
	-- Methods
	create_request_profile_message = function(self)
		return {
			qualifier = player_messages.constants.REQUEST_PROFILE,
		}
	end,
}

return player_messages