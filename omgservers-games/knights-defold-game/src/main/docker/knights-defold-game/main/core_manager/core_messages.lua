local core_messages
core_messages = {
	constants = {
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
		ARENA_SCREEN = "arena_screen",
	},
	-- Methods
	create_game_started_message = function(self)
		return {
		}
	end,
	create_screen_created_message = function(self, screen_qualifier, collection_id)
		return {
			screen_qualifier = screen_qualifier,
			collection_id = collection_id,
		}
	end,
	create_signed_up_message = function(self, user_id, password)
		return {
			user_id = user_id,
			password = password,
		}
	end,
	create_signed_in_message = function(self, client_id)
		return {
			client_id = client_id,
		}
	end,
	create_user_greeted_message = function(self, version_id, version_created)
		return {
			version_id = version_id,
			version_created = version_created,
		}
	end,
	create_runtime_assigned_message = function(self, runtime_qualifier, runtime_id)
		return {
			runtime_qualifier = runtime_qualifier,
			runtime_id = runtime_id,
		}
	end,
	create_message_received_message = function(self, message)
		return {
			message = message,
		}
	end,
	create_connection_upgraded_message = function(self)
		return {
		}
	end,
	create_client_failed_message = function(self, reason)
		return {
			reason = reason
		}
	end,
}

return core_messages