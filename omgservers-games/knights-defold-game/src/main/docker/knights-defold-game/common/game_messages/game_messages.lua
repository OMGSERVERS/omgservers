local game_messages
game_messages = {
	constants = {
		REQUEST_PROFILE = "request_profile",
	},
	-- Methods
	create_request_profile_message = function(self)
		return {
			qualifier = game_messages.constants.REQUEST_PROFILE,
		}
	end,
}

return game_messages