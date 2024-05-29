# OMGSERVERS

[![Build](https://github.com/OMGSERVERS/omgservers/actions/workflows/build.yml/badge.svg)](https://github.com/OMGSERVERS/omgservers/actions/workflows/build.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=OMGSERVERS_omgservers&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=OMGSERVERS_omgservers)

### Installation

- [Docker](https://hub.docker.com/r/omgservers/omgservers-service)

### Environment variables

#### Mandatory variables and their value templates

- OMGSERVERS_DB_URL=postgresql://<db_hostname>:<db_port>/<db_name>
- OMGSERVERS_MQ_HOST=<mq_host>
- OMGSERVERS_EXTERNAL_URI=http://<external_hostname>:<external_port>
- OMGSERVERS_INTERNAL_URI=http://<internal_hostname>:<internal_port>

#### Optional variables and their default values

- OMGSERVERS_DB_USERNAME=root
- OMGSERVERS_DB_PASSWORD=root
- OMGSERVERS_MQ_PORT=5672
- OMGSERVERS_MQ_USERNAME=admin
- OMGSERVERS_MQ_PASSWORD=admin
- OMGSERVERS_MQ_SERVICE_QUEUE=ServiceEvents
- OMGSERVERS_MQ_FORWARDING_QUEUE=ForwardedEvents
- OMGSERVERS_ROOT_LOG_LEVEL=INFO
- OMGSERVERS_APP_LOG_LEVEL=INFO
- OMGSERVERS_TRAFFIC_LOG_LEVEL=INFO
- OMGSERVERS_CONSOLE_LOG_ENABLED=true
- OMGSERVERS_ACCESS_LOG_ENABLED=false
- OMGSERVERS_INDEX_NAME=main
- OMGSERVERS_BOOTSTRAP_SCHEMA_CONCURRENCY=16
- OMGSERVERS_DISABLE_MIGRATION=false
- OMGSERVERS_DISABLE_RELAY_JOB=false
- OMGSERVERS_DATACENTER_ID=0
- OMGSERVERS_INSTANCE_ID=0
- OMGSERVERS_SERVICE_USERNAME=service
- OMGSERVERS_SERVICE_PASSWORD=service
- OMGSERVERS_ADMIN_USERNAME=admin
- OMGSERVERS_ADMIN_PASSWORD=admin
- OMGSERVERS_SHARD_COUNT=1
- OMGSERVERS_BOOTSTRAP_SERVICE=true
- OMGSERVERS_ADDRESSES=http://localhost:8080
- OMGSERVERS_TOKEN_LIFETIME=3600
- OMGSERVERS_CLIENT_INACTIVE_INTERVAL=30
- OMGSERVERS_DISABLE_DOCKER=false
- OMGSERVERS_DOCKER_HOST=tcp://docker:2375
- OMGSERVERS_WORKERS_INACTIVE_INTERVAL=60
- OMGSERVERS_WORKERS_DOCKER_NETWORK=omgservers

### Game project structure

```
config.json - game configuration
lobby.lua - entrypoint and handler for lobby commands
match.lua - entrypoint and handler for match commands
```

### Config schema (config.json)

```
{
  "modes": [
    {
      "name": "<mode_1_name>",
      "minPlayers": <mode_1_min_players>,
      "maxPlayers": <mode_1_max_players>,
      "groups": [
        {
          "name": "<group_1_name>",
          "minPlayers": <group_1_min_players>,
          "maxPlayers": <group_1_max_players>
        }
        ...
        {
          "name": "<group_N_name>",
          "minPlayers": <group_N_min_players>,
          "maxPlayers": <group_N_max_players>
        }
      ]
    },
    ...
    {
        "name": "<mode_N_name>",
        ...
    }
  ]
}
```

### Handler example (lobby.lua or match.lua)

```
function handle_command(self, command)                                            
    if command.qualifier == "handle_message" then
        local var message = command.message            
        return {
            {
                qualifier = "respond_client",
                client_id = command.client_id,
                message = {
                    text = "Hello world!"
                }
            }
        }
    end
end
```

### Incoming commands

```
{
    qualifier = "init_runtime",
    config = {}
}
```

```
{
    qualifier = "update_runtime",
    time = <time>    
}
```

```
{
    qualifier = "add_client",
    client_id = <client_id>,
    attributes = {},
    profile = {},
    group_name = <group_name> -- optional, it exists if client was added to the match runtime  
}
```

```
{
    qualifier = "delete_client",
    client_id = <client_id>    
}
```

```
{
    qualifier = "handle_message",
    client_id = <client_id>,    
    message = {}
}
```

### Outgoing commands

```
{
    qualifier = "respond_client",
    client_id = <client_id>,
    message = {}
}
```

```
{
    qualifier = "set_attributes",
    client_id = <client_id>,
    attributes = {}
}
```

```
{
    qualifier = "set_profile",
    client_id = <client_id>,
    profile = {}
}
```

```
{
    qualifier = "multicast_message",
    clients = {<client_1_id>, ..., <client_N_id> }
    message = {}
}
```

```
{
    qualifier = "broadcast_message",
    message = {}
}
```

```
{
    qualifier = "kick_client",   
    client_id = <client_id>
}
```

```
{
    qualifier = "stop_matchmaking",    
    reason = "<reason>"    
}
```

### Command line tool

```
./omgserversctl
OMGSERVERS ctl, v1.0.0
Usage:
 omgserversctl help
 omgserversctl logs
 omgserversctl env print
 omgserversctl env useLocal
 omgserversctl admin pingServer
 omgserversctl admin generateId
 omgserversctl admin createTenant
 omgserversctl admin createDeveloper
 omgserversctl developer createToken
 omgserversctl developer createProject
 omgserversctl developer getTenantDashboard
 omgserversctl developer uploadVersion <scripts_path>
```