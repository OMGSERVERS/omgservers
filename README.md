# OMGSERVERS

[![Build](https://github.com/OMGSERVERS/omgservers/actions/workflows/build.yml/badge.svg)](https://github.com/OMGSERVERS/omgservers/actions/workflows/build.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=OMGSERVERS_omgservers&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=OMGSERVERS_omgservers)

### Installation

- [Docker](https://hub.docker.com/r/omgservers/omgservers-service)

### Environment variables

#### Have to be set

- OMGSERVERS_DATASOURCE_URL
- OMGSERVERS_DATASOURCE_USERNAME
- OMGSERVERS_DATASOURCE_PASSWORD
- OMGSERVERS_EXTERNAL_URI
- OMGSERVERS_INTERNAL_URI
- OMGSERVERS_ADDRESSES
- OMGSERVERS_WORKERS_NETWORK

#### Optional, with default values

- OMGSERVERS_ROOT_LOG_LEVEL:INFO
- OMGSERVERS_APP_LOG_LEVEL:INFO
- OMGSERVERS_TRAFFIC_LOG_LEVEL:INFO
- OMGSERVERS_CONSOLE_LOG_ENABLED:true
- OMGSERVERS_ACCESS_LOG_ENABLED:false
- OMGSERVERS_SCHEDULER_ENABLED:true
- OMGSERVERS_INDEX_NAME:main
- OMGSERVERS_MIGRATION_CONCURRENCY:16
- OMGSERVERS_DISABLE_MIGRATION:false
- OMGSERVERS_HANDLER_COUNT:4
- OMGSERVERS_HANDLER_LIMIT:16
- OMGSERVERS_DATACENTER_ID:0
- OMGSERVERS_INSTANCE_ID:0
- OMGSERVERS_SERVICE_USERNAME:service
- OMGSERVERS_SERVICE_PASSWORD:service
- OMGSERVERS_ADMIN_USERNAME:admin
- OMGSERVERS_ADMIN_PASSWORD:admin
- OMGSERVERS_SHARD_COUNT:1
- OMGSERVERS_BOOTSTRAP_SERVICE:true
- OMGSERVERS_TOKEN_LIFETIME:3600
- OMGSERVERS_INACTIVE_INTERVAL:30
- OMGSERVERS_DISABLE_DOCKER:false
- OMGSERVERS_DOCKER_HOST:tcp://docker:2375

### Game project structure

```
config.json - game configuration
lobby.lua - entrypoint and handler for lobby commands
match.lua - entrypoint and handler for match commands
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
    profile = {}
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
    qualifier = "respond",
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
    qualifier = "multicast",
    clients = {<client_1_id>, ..., <client_N_id> }
    message = {}
}
```

```
{
    qualifier = "broadcast",
    message = {}
}
```

```
{
    qualifier = "kick",   
    client_id = <client_id>
}
```

```
{
    qualifier = "stop",    
    reason = "<reason>"    
}
```

### Config schema

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