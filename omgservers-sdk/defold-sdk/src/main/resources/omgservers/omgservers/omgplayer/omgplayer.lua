local omgconstants = require("omgservers.omgplayer.omgconstants")
local omgmethods = require("omgservers.omgplayer.omgmethods")

local omgplayer
omgplayer = {
	constants = omgconstants,
	-- Methods
	init = function(self, options)
		omgmethods:init(options)
	end,
	sign_up = function(self)
		omgmethods:sign_up()
	end,
	sign_in = function(self, user_id, password)
		omgmethods:sign_in(user_id, password)
	end,
	send_service_message = function(self, message)
		omgmethods:send_service_message(message)
	end,
	send_text_message = function(self, message)
		omgmethods:send_text_message(message)
	end,
	send_binary_message = function(self, buffer)
		omgmethods:send_binary_message(buffer)
	end,
	update = function(self, dt)
		omgmethods:update(dt)
	end,
}

return omgplayer
