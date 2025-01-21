local omghttp
omghttp = {
	--[[
		self,
		options = {
			config, -- omgconfig instance
		},
	]]--
	create = function(self, options)
		assert(self, "The self must not be nil.")
		assert(options, "The options must not be nil.")
		assert(options.config, "The value config must not be nil.")
		assert(options.config.type == "omgconfig", "The type of config must be omgconfig")

		local trace_logging = options.config.trace_logging

		local state = options.state
		
		return {
			type = "omghttp",
			-- Methods
			decode_response = function(instance, response_body)
				local status, result = pcall(json.decode, response_body)
				if status then
					return result
				else
					return nil, result
				end
			end,
			build_final_handler = function(instance, response_handler, failure_handler)
				return function(_, id, response)
					local response_status = response.status
					local response_body = response.response

					if trace_logging then
						print(socket.gettime() .. " [OMGPLAYER] Response, status=" .. response_status .. ", body=" .. response_body)
					end

					local decoded_body, decoding_error = instance:decode_response(response_body)
					if decoding_error then
						failure_handler(response_status, nil, decoding_error)
					else
						if response_status >= 200 and response_status < 300 then
							response_handler(response_status, decoded_body)
						else
							failure_handler(response_status, decoded_body)
						end
					end
				end
			end,
			build_retriable_handler = function(instance, url, method, request_headers, request_body, response_handler, failure_handler, retries)
				return function(_, id, response)
					local response_status = response.status
					local response_body = response.response

					if trace_logging then
						print(socket.gettime() .. " [OMGPLAYER] Response, status=" .. response_status .. ", body=" .. response_body)
					end

					local decoded_body, decoding_error = instance:decode_response(response_body)
					if decoding_error then
						failure_handler(response_status, nil, decoding_error)
					else
						if response_status >= 200 and response_status < 300 then
							response_handler(response_status, decoded_body)
						else
							local handler
							if retries > 0 then
								handler = instance:build_retriable_handler(url, method, request_headers, request_body, response_handler, failure_handler, retries - 1)
							else
								handler = instance:build_final_handler(response_handler, failure_handler)
							end

							http.request(url, method, handler, request_headers, request_body)
						end
					end
				end
			end,
			request_server = function(instance, url, request_body, response_handler, failure_handler, retries, request_token)
				local request_headers = {
					["Content-Type"] = "application/json"
				}

				if request_token then
					request_headers["Authorization"] = "Bearer " .. request_token
				end

				local is_empty = next(request_body) == nil
				local endoded_body = json.encode(request_body, {
					encode_empty_table_as_object = is_empty
				})

				local method = "POST"

				if trace_logging then
					print(socket.gettime() .. " [OMGPLAYER] Request, " .. method .. " " .. url .. ", body=" .. endoded_body)
				end

				local retriable_handler = instance:build_retriable_handler(url, method, request_headers, endoded_body, response_handler, failure_handler, retries)
				http.request(url, method, retriable_handler, request_headers, endoded_body)
			end
		}
	end
}

return omghttp
