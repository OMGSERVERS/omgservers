local omgdispatcher
omgdispatcher = {
	--[[
		self,
		options = {
			config, -- omgconfig instance
			events, -- omgevents instance
		},
	]]--
	create = function(self, options)
		assert(self, "The self must not be nil.")
		assert(options, "The options must not be nil.")
		assert(options.config, "The value config must not be nil.")
		assert(options.config.type == "omgconfig", "The type of config must be omgconfig")
		assert(options.events, "The value events must not be nil.")
		assert(options.events.type == "omgevents", "The type of events must be omgevents")

		local debug_logging = options.config.debug_logging
		local service_url = options.config.service_url
	
		local events = options.events
		
		local dispatcher_url = service_url .. "/omgservers/v1/entrypoint/dispatcher/connection"

		return {
			type = "omgdispatcher",
			connection = nil,
			-- Methods
			connect = function(instance, ws_token, callback)
				local connection_url = dispatcher_url .. "?ws_token=" .. ws_token
				local params = {
					protocol = "omgservers"
				}

				if debug_logging then
					print(socket.gettime() .. " [OMGSERVER] Connect to the dispatcher, url=" .. connection_url)
				end

				local connection = websocket.connect(connection_url, params, function(_, _, data)
					if data.event == websocket.EVENT_DISCONNECTED then
						if debug_logging then
							print(socket.gettime() .. " [OMGSERVER] The connection to the dispatcher was disconnected")
							pprint(data)
						end

					elseif data.event == websocket.EVENT_CONNECTED then
						if debug_logging then
							print(socket.gettime() .. " [OMGSERVER] Connected to the dispatcher")
						end
						
						if callback then
							callback()
						end

					elseif data.event == websocket.EVENT_ERROR then
						events:failed("dispatcher failed, message=" .. data.message)

					elseif data.event == websocket.EVENT_MESSAGE then
						events:message_received(data.message)
					end
				end)

				instance.connection = connection
			end,
			send_text_message = function(instance, message)
				assert(instance.connection, "The dispatcher must be connected")
				assert(type(message) == "string", "The type of message must be string")

				websocket.send(instance.connection, message, {
					type = websocket.DATA_TYPE_TEXT
				})
			end,
			send_binary_buffer = function(instance, buffer)
				assert(instance.connection, "The dispatcher must be connected")
				assert(type(buffer) == "string", "The type of buffer must be string")

				websocket.send(instance.connection, buffer, {
					type = websocket.DATA_BINARY_TEXT
				})
			end,
		}
	end
}

return omgdispatcher