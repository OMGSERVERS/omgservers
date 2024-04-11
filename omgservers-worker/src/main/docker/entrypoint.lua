local socket = require("socket")
local http = require("socket.http")
local ltn12 = require("ltn12")
local json = require("cjson")
local inspect = require("inspect")
local base64 = require("base64")

json.encode_empty_table_as_object(false)

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

local function log(...)
    print(os.date("%Y-%m-%d %H:%M:%S"), "[LUA]", ...)
end

log("Service url", service_url)
log("User id", user_id)
log("Password", password)
log("Runtime id", runtime_id)
log("Runtime qualifier", runtime_qualifier)

local create_token_url = service_url .. "/omgservers/v1/entrypoint/worker/request/create-token";
local get_version_url = service_url .. "/omgservers/v1/entrypoint/worker/request/get-version";
local interchange_url = service_url .. "/omgservers/v1/entrypoint/worker/request/interchange";

local function request_endpoint(request_url, request_body, request_token)
    local encoded_body = json.encode(request_body)

    local request_headers = {
        ["Content-Type"] = "application/json",
        ["Content-Length"] = #encoded_body,
    }

    if request_token then
        request_headers["Authorization"] = "Bearer " .. request_token
    end

    log("Request endpoint", request_url)
    log("Request body", inspect(encoded_body, {newline=""}))

    local response_table = {}
    local body, code, headers, status = http.request({
        method = "PUT",
        headers = request_headers,
        url = request_url,
        source = ltn12.source.string(encoded_body),
        sink = ltn12.sink.table(response_table)
    })

    if code == 200 then
        local response_string = table.concat(response_table)
        log("Request response", inspect(response_string, {newline=""}))
        local decoded_response = json.decode(response_string)
        return decoded_response, code
    else
        return nil, code
    end
end

local function create_token()
    local request_body = {
        user_id = user_id,
        password = password
    }

    local response_body, status_code = request_endpoint(create_token_url, request_body)
    if response_body then
        local raw_token = response_body["raw_token"]
        log("Token was created, ", raw_token)
        return raw_token
    else
        return nil, "token was not created, status_code=" .. status_code
    end
end

local function get_version(raw_token)
    if not raw_token then
        error("raw token is nil")
    end

    local request_body = {
        runtime_id = runtime_id
    }

    local response_body, status_code = request_endpoint(get_version_url, request_body, raw_token)
    if response_body then
        local version_id = response_body.version.id
        log("Version was got", string.format("%.0f", version_id))
        return response_body.version
    else
        return nil, "version was not got, status_code=" .. status_code
    end
end

local function interchange(raw_token, outgoing_commands, consumed_commands_ids)
    if not raw_token then
        error("raw token is nil")
    end

    local request_body = {
        runtime_id = runtime_id,
        outgoing_commands = outgoing_commands,
        consumed_commands = consumed_commands_ids
    }

    local response_body, status_code = request_endpoint(interchange_url, request_body, raw_token)
    if response_body then
        local incoming_commands = response_body.incoming_commands
        log("Interchanged", inspect(incoming_commands, {newline=""}))
        return incoming_commands
    else
        return nil, "interchange failed, status_code=" .. status_code
    end
end

local raw_token, error_text = create_token()
if not raw_token then
    error(error_text)
end

local version, error_text = get_version(raw_token)
if not version then
    error(error_text)
end

local source_code = {}
for index, file in ipairs(version.source_code.files) do
    local file_name = file.file_name
    local base64content = file.base64content
    local file_content = base64.decode(base64content)
    log("File name", file_name)
    log("Base64 content", base64content)
    log("File content", file_content)
    source_code[file_name] = file_content
end

local custom_loader = function(module_name)
    local file_name = module_name .. ".lua"
    log("Search module", module_name)
    if source_code[file_name] then
        return assert(loadstring(source_code[file_name]))
    else
        return nil, "module was not found, " .. module_name
    end
end
table.insert(package.loaders, custom_loader)

local module_lua
if runtime_qualifier == "LOBBY" then
    module_lua = require("lobby")
elseif runtime_qualifier == "MATCH" then
    module_lua = require("match")
else
    error("unknown runtime qualifier" .. runtime_qualifier)
end

local outgoing_commands = {}
local consumed_commands_ids = {}

local handler_self = {}
local finished = false
while not finished do
    local incoming_commands, error_text = interchange(raw_token, outgoing_commands, consumed_commands_ids)
    if not incoming_commands then
        error(error_text)
    end

    consumed_commands_ids = {}
    for index, incoming_command in ipairs(incoming_commands) do
        local id = incoming_command.id
        local qualifier = incoming_command.qualifier
        local command_body = incoming_command.body
        log("Handle command", string.format("%.0f", id), qualifier)
        log("Command body", inspect(command_body, {newline=""}))
        local result_commands = handle_command(handler_self, command_body)
        if result_commands then
            log("Result commands", inspect(result_commands))
            for result_command_index, result_command in ipairs(result_commands) do
                outgoing_commands[#outgoing_commands + 1] = result_command
            end
        end

        consumed_commands_ids[#consumed_commands_ids + 1] = id
    end

    log("Consumed", inspect(consumed_commands_ids, {newline=""}))

    socket.sleep(1)
end

log("Finished")