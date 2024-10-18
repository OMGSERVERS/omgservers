local omgconstants = require("omgservers.omgplayer.omgconstants")
local omginstance = require("omgservers.omgplayer.omginstance")

local omgplayer
omgplayer = {
	constants = omgconstants,
	-- Methods
	init = function(self, options)
		omginstance:init(options)
	end,
	reset = function(self)
		omginstance:reset()
	end,
	sign_up = function(self)
		omginstance:sign_up()
	end,
	sign_in = function(self, user_id, password)
		omginstance:sign_in(user_id, password)
	end,
	send_service_message = function(self, message)
		omginstance:send_service_message(message)
	end,
	send_text_message = function(self, message)
		omginstance:send_text_message(message)
	end,
	send_binary_message = function(self, buffer)
		omginstance:send_binary_message(buffer)
	end,
	update = function(self, dt)
		omginstance:update(dt)
	end,
}

return omgplayer
