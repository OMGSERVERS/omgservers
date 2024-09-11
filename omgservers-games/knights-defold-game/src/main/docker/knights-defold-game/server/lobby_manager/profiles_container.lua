local profiles_container
profiles_container = {
	wrapped_profiles = {},
	-- methods
	set_profile = function(self, client_id, wrapped_profile)
		self.wrapped_profiles[client_id] = wrapped_profile
		print("[PROFILE_CONTAINER] Profile was set, client_id=" .. client_id .. ", nickname=" .. wrapped_profile.profile.data.nickname)
	end,
	get_profile = function(self, client_id)
		return self.wrapped_profiles[client_id]
	end,
	delete_profile = function(self, client_id)
		if self.wrapped_profiles[client_id] then
			self.wrapped_profiles[client_id] = nil
			print("[PROFILE_CONTAINER] Profile was deleted, client_id=" .. client_id)
		else
			print("[PROFILE_CONTAINER] Profile was not found to delete, client_id=" .. client_id)
		end
	end,
}

return profiles_container