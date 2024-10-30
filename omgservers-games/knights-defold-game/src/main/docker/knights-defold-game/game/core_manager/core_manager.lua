local core_state = require("game.core_manager.core_state")

local core_manager
core_manager = {
	GAME_STARTED = "game_started",
	SCREEN_CREATED = "screen_created",
	SIGNED_UP = "signed_up",
	SIGNED_IN = "signed_in",
	USER_GREETED = "user_greeted",
	RUNTIME_ASSIGNED = "runtime_assigned",
	MESSAGE_RECEIVED = "message_received",
	CONNECTION_UPGRADED = "connection_upgraded",
	CLIENT_FAILED = "client_failed",
	-- Screen qualifiers
	AUTH_SCREEN = "auth_screen",
	LOBBY_SCREEN = "lobby_screen",
	MATCH_SCREEN = "match_screen",
	WAIT_SCREEN = "wait_screen",
	-- Methods
	get_client_id = function(self)
		return core_state.client_id
	end,
	game_started = function(self, receiver)
		msg.post(receiver, core_manager.GAME_STARTED, {
		})
	end,
	screen_created = function(self, receiver, screen_qualifier, collection_id)
		msg.post(receiver, core_manager.SCREEN_CREATED, {
			screen_qualifier = screen_qualifier,
			collection_id = collection_id,
		})
	end,
	signed_up = function(self, receiver, user_id, password)
		msg.post(receiver, core_manager.SIGNED_UP, {
			user_id = user_id,
			password = password,
		})
	end,
	signed_in = function(self, receiver, client_id)
		msg.post(receiver, core_manager.SIGNED_IN, {
			client_id = client_id,
		})
	end,
	user_greeted = function(self, receiver, version_id, version_created)
		msg.post(receiver, core_manager.USER_GREETED, {
			version_id = version_id,
			version_created = version_created,
		})
	end,
	runtime_assigned = function(self, receiver, runtime_qualifier, runtime_id)
		msg.post(receiver, core_manager.RUNTIME_ASSIGNED, {
			runtime_qualifier = runtime_qualifier,
			runtime_id = runtime_id,
		})
	end,
	message_received = function(self, receiver, message)
		-- send message as is
		msg.post(receiver, core_manager.MESSAGE_RECEIVED, message)
	end,
	connection_upgraded = function(self, receiver)
		msg.post(receiver, core_manager.CONNECTION_UPGRADED, {
		})
	end,
	client_failed = function(self, receiver, reason)
		msg.post(receiver, core_manager.CLIENT_FAILED, {
			reason = reason
		})
	end,
}

return core_manager