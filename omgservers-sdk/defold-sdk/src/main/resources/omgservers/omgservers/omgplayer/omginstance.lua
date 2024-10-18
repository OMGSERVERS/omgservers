local omgconfig = require("omgservers.omgplayer.omgconfig")
local omghttp = require("omgservers.omgplayer.omghttp")
local omgevents = require("omgservers.omgplayer.omgevents")
local omgclient = require("omgservers.omgplayer.omgclient")
local omgdispatcher = require("omgservers.omgplayer.omgdispatcher")
local omgprocess = require("omgservers.omgplayer.omgprocess")

local omginstance
omginstance = {
	config = nil,
	http = nil,
	events = nil,
	client = nil,
	dispatcher = nil,
	process = nil,
	-- Methods
	init = function(self, options)
		assert(self, "The self must not be nil.")
		assert(options, "The options must not be nil.")

		self.config = omgconfig:create(options)
		self:reset()
	end,
	reset = function(self)
		assert(self, "The self must not be nil.")

		if self.dispatcher then
			self.dispatcher:disconnect()
		end
		
		self.http = omghttp:create({
			config = self.config,
		})

		self.events = omgevents:create({
			config = self.config,
		})

		self.client = omgclient:create({
			config = self.config,
			http = self.http,
			events = self.events,
		})

		self.dispatcher = omgdispatcher:create({
			config = self.config,
			events = self.events,
		})

		self.process = omgprocess:create({
			config = self.config,
			events = self.events,
			client = self.client,
			dispatcher = self.dispatcher,
		})
	end,
	sign_up = function(self)
		assert(self, "The self must not be nil.")
		assert(self.config, "The player must be initialized")

		self.client:create_user(function(user_id, password)
			self.events:signed_up(user_id, password)
		end)
	end,
	sign_in = function(self, user_id, password)
		assert(self, "The self must not be nil.")
		assert(user_id, "The user_id must not be nil.")
		assert(password, "The password must not be nil.")
		assert(self.config, "The player must be initialized")

		self.client:set_user(user_id, password)

		self.client:create_token(function(api_token)
			self.client:create_client(function(client_id)
				self.events:signed_in(client_id)
			end)
		end)
	end,
	send_service_message = function(self, message)
		assert(self, "The self must not be nil.")
		assert(message, "The message must not be nil.")
		assert(self.config, "The player must be initialized")

		self.client:send_message(message)
	end,
	send_text_message = function(self, message)
		assert(self, "The self must not be nil.")
		assert(message, "The message must not be nil.")
		assert(self.config, "The player must be initialized")

		self.dispatcher:send_text_message(message)
	end,
	send_binary_message = function(self, buffer)
		assert(self, "The self must not be nil.")
		assert(buffer, "The buffer must not be nil.")
		assert(self.config, "The player must be initialized")

		self.dispatcher:send_binary_buffer(buffer)
	end,
	update = function(self, dt)
		assert(self, "The self must not be nil.")
		assert(self, "The dt must not be nil.")
		assert(type(dt) == "number", "The type of dt must be number")
		assert(self.config, "The player must be initialized")

		self.process:update(dt)
	end,
}

return omginstance