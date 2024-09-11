local profile_wrapper
profile_wrapper = {
	-- Methods
	migrate = function(self, profile)
		local profile_version = profile.version
		if profile_version == 1 then
			print("[PROFILE_WRAPPER] Profile is up-to-date, nickname=" .. profile.data.nickname)
		else
			print("[PROFILE_WRAPPER] Migration for version is not supported, nickname=" .. profile.data.nickname .. ", version=" .. profile_version)
		end
	end,
	wrap = function(self, profile)
		self:migrate(profile)

		return {
			profile = profile,
			-- methods
			change_nickname = function(wrapper, new_nickname)
				local old_nickname = profile.data.nickname
				profile.data.nickname = string.sub(new_nickname, 1, 16)
				print("[PROFILE_WRAPPER] Nickname was changed, old_nickname=" .. old_nickname .. ", new_nickname=" .. profile.data.nickname)
			end
		}
	end,
	create = function(self, nickname)
		local new_profile = {
			version = 1,
			data = {
				nickname = nickname,
				points = 0,
			}
		}
		print("[PROFILE_WRAPPER] Profile was created, nickname=" .. nickname)

		return self:wrap(new_profile)
	end,
}

return profile_wrapper