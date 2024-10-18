local omgstate
omgstate = {
	--[[
		self,
		options = {
		},
	]]--
	create = function(self, options)
		assert(self, "The self must not be nil.")
		assert(options, "The options must not be nil.")
		
		return {
			greeted = false,
			lobby_id = nil,
			matchmaker_id = nil,
			match_id = nil,
			-- Methods
			greet = function(instance)
				instance.greeted = true
			end,
			set_lobby_id = function(instance, assigned_lobby_id)
				instance.lobby_id = assigned_lobby_id
				instance.match_id = nil
			end,
			set_matchmaker_id = function(instance, assigned_matchmaker_id)
				instance.matchmaker_id = assigned_matchmaker_id
			end,
			set_match_id = function(instance, assigned_match_id)
				instance.lobby_id = nil
				instance.match_id = assigned_match_id
			end,
			get_runtime_id = function(instance)
				return instance.lobby_id or instance.match_id
			end,
		}
	end
}

return omgstate