local socket = require("socket")
local http = require("socket.http")
local ltn12 = require("ltn12")

local json = require("cjson")
json.encode_empty_table_as_object(false)

local inspect = require("inspect")
local base64 = require("base64")

local settings = {
    debug = true,
    trace = false,
    iterate_delay = 0.25,
}

local internal = {
    log = function(self, text)
        print(os.date("%Y-%m-%d %H:%M:%S") .. " [OMGS] " .. text)
        io.stdout:flush()
    end,
}

local components = {
    create_server_environment = function(self, service_url, user_id, password, runtime_id, qualifier)
        return {
            service_url = service_url,
            user_id = user_id,
            password = password,
            runtime_id = runtime_id,
            qualifier = qualifier,
        }
    end,
    create_service_urls = function(self, service_url)
        return {
            service_url = service_url,
            create_token_url = service_url .. "/omgservers/v1/entrypoint/worker/request/create-token",
            get_version_url = service_url .. "/omgservers/v1/entrypoint/worker/request/get-version",
            interchange_url = service_url .. "/omgservers/v1/entrypoint/worker/request/interchange",
        }
    end,
    create_server_state = function(self)
        return {
            outgoing_commands = {},
            consumed_commands = {},
            -- methods
            add_outgoing_command = function(self, command)
                self.outgoing_commands[#self.outgoing_commands + 1] = command
            end,
            add_consumed_command = function(self, command)
                assert(command.id, "Consumed command must have id")
                self.consumed_commands[#self.consumed_commands + 1] = command.id
            end,
            pull_outgoing_commands = function(self)
                local outgoing_commands = self.outgoing_commands
                self.outgoing_commands = {}
                return outgoing_commands
            end,
            pull_consumed_commands = function(self)
                local consumed_commands = self.consumed_commands
                self.consumed_commands = {}
                return consumed_commands
            end,
        }
    end,
}

local exchanger = {
    -- methods
    request_endpoint = function(self, request_url, request_body, request_token)
        local encoded_body = json.encode(request_body)

        local request_headers = {
            ["Content-Type"] = "application/json",
            ["Content-Length"] = #encoded_body,
        }

        if request_token then
            request_headers["Authorization"] = "Bearer " .. request_token
        end

        local response_table = {}
        local method = "PUT"

        if settings.trace then
            internal:log("Request, " .. method .. " " .. request_url .. ", body=" .. encoded_body)
        end

        local body, code, headers, status = http.request({
            method = method,
            headers = request_headers,
            url = request_url,
            source = ltn12.source.string(encoded_body),
            sink = ltn12.sink.table(response_table)
        })

        if code >= 200 and code < 300 then
            local response_string = table.concat(response_table)

            if settings.trace then
                internal:log("Response, " .. method .. " " .. request_url .. ", body=" .. response_string)
            end

            local decoded_response = json.decode(response_string)
            return decoded_response, code
        else
            return nil, code
        end
    end,
}

local server = {
    components = {},
    -- methods
    bootstrap = function(self)
        local service_url = os.getenv("OMGSERVERS_URL")
        if not service_url then
            error("service_url is nil")
        end

        local user_id = os.getenv("OMGSERVERS_USER_ID")
        if not user_id then
            error("user_id is nil")
        end

        local password = os.getenv("OMGSERVERS_PASSWORD")
        if not password then
            error("password is nil")
        end

        local runtime_id = os.getenv("OMGSERVERS_RUNTIME_ID")
        if not runtime_id then
            error("runtime_id is nil")
        end

        local runtime_qualifier = os.getenv("OMGSERVERS_RUNTIME_QUALIFIER")
        if not runtime_qualifier then
            error("runtime_qualifier is nil")
        end

        self.components.server_environment = components:create_server_environment(service_url, user_id, password, runtime_id, runtime_qualifier)
        internal:log("Environment, service_url=" .. service_url)
        internal:log("Environment, user_id=" .. user_id)
        internal:log("Environment, password=" .. string.sub(password, 1, 4) .. "..")
        internal:log("Environment, runtime_id=" .. runtime_id)
        internal:log("Environment, runtime_qualifier=" .. runtime_qualifier)

        self.components.service_urls = components:create_service_urls(service_url)
        self.components.server_state = components:create_server_state()
    end,
    create_token = function(self)
        local self_components = self.components
        assert(self_components.server_environment, "Server environment must be set")
        assert(self_components.service_urls, "Service urls must be set")

        local user_id = self_components.server_environment.user_id
        local password = self_components.server_environment.password

        local request_body = {
            user_id = user_id,
            password = password
        }

        local request_url = self_components.service_urls.create_token_url
        local response_body, status_code = exchanger:request_endpoint(request_url, request_body)
        if response_body then
            local raw_token = response_body["raw_token"]
            internal:log("Token was created, raw_token=" .. string.sub(raw_token, 1, 4) .. "..")
            return raw_token
        else
            return nil, "token was not created, status_code=" .. status_code
        end
    end,
    interchange = function(self, raw_token, outgoing_commands, consumed_commands)
        local self_components = self.components
        assert(self_components.server_environment, "Server environment must be set")
        assert(self_components.service_urls, "Service urls must be set")

        if not raw_token then
            error("raw token is nil")
        end

        local runtime_id = self_components.server_environment.runtime_id

        local request_body = {
            runtime_id = runtime_id,
            outgoing_commands = outgoing_commands,
            consumed_commands = consumed_commands
        }

        local request_url = self_components.service_urls.interchange_url
        local response_body, status_code = exchanger:request_endpoint(request_url, request_body, raw_token)
        if response_body then
            local incoming_commands = response_body.incoming_commands
            return incoming_commands
        else
            return nil, "interchange failed, status_code=" .. status_code
        end
    end,
    handle_command = function(self, handler, command_qualifier, command_body)
        local server_state = self.components.server_state
        assert(server_state, "Server state must be created")

        local outgoing_commands = handler:handle(command_qualifier, command_body)
        if outgoing_commands then
            if settings.debug then
                internal:log("Outgoing commands, outgoing_commands=" .. inspect(outgoing_commands, {newline="", indent=""}))
            end
            for command_index, outgoing_command in ipairs(outgoing_commands) do
                server_state:add_outgoing_command(outgoing_command)
            end
        end
    end,
    iterate = function(self, raw_token, handler)
        local server_state = self.components.server_state
        assert(server_state, "Server state must be created")

        local outgoing_commands = server_state:pull_outgoing_commands()
        local consumed_commands = server_state:pull_consumed_commands()

        local incoming_commands, error_text = self:interchange(raw_token, outgoing_commands, consumed_commands)
        if error_text then
            error(error_text)
        end

        for index, incoming_command in ipairs(incoming_commands) do
            local id = incoming_command.id
            local command_qualifier = incoming_command.qualifier
            local command_body = incoming_command.body
            if settings.debug then
                internal:log("Handle command, id=" .. string.format("%.0f", id) .. ", qualifier=" .. command_qualifier .. ", body=" .. inspect(command_body, {newline="", indent=""}))
            end
            self:handle_command(handler, command_qualifier, command_body)

            server_state:add_consumed_command(incoming_command)
        end

        if settings.trace and #server_state.consumed_commands > 0 then
            internal:log("Consumed, ids=" .. inspect(server_state.consumed_commands, {newline="", indent=""}))
        end

        if settings.trace then
            internal:log("Update runtime, qualifier=UPDATE_RUNTIME")
        end
        self:handle_command(handler, "UPDATE_RUNTIME", {})
    end,
    enter_loop = function(self, handler)
        local raw_token, error_text = self:create_token()
        if error_text then
            error(error_text)
        end

        internal:log("Loop started")

        local finished = false
        while not finished do
            self:iterate(raw_token, handler)

            socket.sleep(settings.iterate_delay)
        end

        internal:log("Loop finished")
    end,
}
server:bootstrap()

local entrypoint = {
    runtime_id = server.components.server_environment.runtime_id,
    qualifier = server.components.server_environment.qualifier,
    -- methods
    log = function(self, text)
        print(os.date("%Y-%m-%d %H:%M:%S") .. " [GAME] " .. text)
    end,
    enter_loop = function(self, handler)
        server:enter_loop(handler)
    end,
}

return entrypoint
