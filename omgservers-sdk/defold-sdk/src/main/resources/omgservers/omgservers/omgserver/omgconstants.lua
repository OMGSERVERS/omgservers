local omgconstants
omgconstants = {
	-- Exit codes
	ENVIRONMENT_EXIT_CODE = 1,
	TOKEN_EXIT_CODE = 2,
	CONFIG_EXIT_CODE = 3,
	API_EXIT_CODE = 4,
	WS_EXIT_CODE = 5,
	-- Environment variables
	SERVICE_URL = "OMGSERVERS_SERVICE_URL",
	RUNTIME_ID = "OMGSERVERS_RUNTIME_ID",
	PASSWORD = "OMGSERVERS_PASSWORD",
	RUNTIME_QUALIFIER = "OMGSERVERS_RUNTIME_QUALIFIER",
	-- Runtime qualifiers
	LOBBY = "LOBBY",
	MATCH = "MATCH",
	-- Event qualifiers
	SERVER_STARTED = "SERVER_STARTED",
	COMMAND_RECEIVED = "COMMAND_RECEIVED",
	MESSAGE_RECEIVED = "MESSAGE_RECEIVED",
	-- Runtime command qualifiers
	INIT_RUNTIME = "INIT_RUNTIME",
	ADD_CLIENT = "ADD_CLIENT",
	ADD_MATCH_CLIENT = "ADD_MATCH_CLIENT",
	DELETE_CLIENT = "DELETE_CLIENT",
	HANDLE_MESSAGE = "HANDLE_MESSAGE",
	-- Outgoing command qualifiers
	RESPOND_CLIENT = "RESPOND_CLIENT",
	SET_ATTRIBUTES = "SET_ATTRIBUTES",
	SET_PROFILE = "SET_PROFILE",
	MULTICAST_MESSAGE = "MULTICAST_MESSAGE",
	BROADCAST_MESSAGE = "BROADCAST_MESSAGE",
	KICK_CLIENT = "KICK_CLIENT",
	REQUEST_MATCHMAKING = "REQUEST_MATCHMAKING",
	STOP_MATCHMAKING = "STOP_MATCHMAKING",
	UPGRADE_CONNECTION = "UPGRADE_CONNECTION",
	-- Upgrade protocols
	DISPATCHER_PROTOCOL = "DISPATCHER",
	BASE64_ENCODED = "B64",
	PLAIN_TEXT = "TXT",
}

return omgconstants