# OMGSERVERS

[![Build](https://github.com/OMGSERVERS/omgservers/actions/workflows/build.yml/badge.svg)](https://github.com/OMGSERVERS/omgservers/actions/workflows/build.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=OMGSERVERS_omgservers&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=OMGSERVERS_omgservers)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=OMGSERVERS_omgservers&metric=bugs)](https://sonarcloud.io/summary/new_code?id=OMGSERVERS_omgservers)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=OMGSERVERS_omgservers&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=OMGSERVERS_omgservers)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=OMGSERVERS_omgservers&metric=coverage)](https://sonarcloud.io/summary/overall?id=OMGSERVERS_omgservers)
![Docker Pulls](https://img.shields.io/docker/pulls/omgservers/service)

OMGSERVERS is a backend for authoritative game servers.

# Principles

- Monolithic Architecture.
- Sharding Over Clustering.
- No Vendor Lock-in.

# Features

- Developer account and permission management.
- Support for tenants, projects, stages, versions, and deployments.
- Two types of game runtimes: lobbies and matches.
- Game runtimes execution in Docker containers.
- Players assignment across lobbies and matches.
- Players data storage and management.
- Defold SDK support for both clients and servers.
- A command-line tool for administrative tasks.

# User Roles

- Admin – Manages backend installations.
- Support – Creates tenants for developers.
- Developer – Push game server docker images.
- Player – Plays games developed by developers.
- Runtime – Interacts with the backend and handles gameplay.

# How it works

- Developers push game runtime Docker images to the registry.
- When players connect, the backend runs and assigns lobbies to them.
- To enable multiplayer gameplay, the backend runs matches.
- Lobbies and matches use the OMGSERVER SDK to perform backend-specific commands.
- Players use the OMGPLAYER SDK to interact with the backend.
- All incoming player messages are routed to assigned runtimes.
- To implement real-time gameplay, matches require players to connect directly.

# Installation Types

| **Type**                        | **Description**                                          | **Suitable for**                                                         |
|---------------------------------|----------------------------------------------------------|--------------------------------------------------------------------------|
| **Standalone**                  | Backend single-server installation.                      | It is acceptable to distribute players across independent installations. |
|                                 | Shared server for game runtimes and backend.             | Non-resource-intensive game runtimes.                                    |
| **Standalone with server pool** | Backend single-server installation.                      | It is acceptable to distribute players across independent installations. |
|                                 | Pool of Docker hosts for game runtimes.                  | Resource-intensive game runtimes.                                        |
| **Sharded**                     | Distribute data across multiple servers by using shards. | A significant number of players should be able to play together.         |
|                                 | Support on-demand infrastructure scaling.                | Non-resource-intensive game runtimes.                                    |
|                                 | Shared servers for game runtimes and backend.            |                                                                          |
| **Sharded with server pool**    | Distribute data across multiple servers by using shards. | A significant number of players should be able to play together.         |
|                                 | Support on-demand infrastructure scaling.                | Resource-intensive game runtimes.                                        |
|                                 | Pool of Docker hosts for game runtimes.                  |                                                                          |
| **Public cloud**                | Provide a pay-as-you-go service.                         |                                                                          |
|                                 | On-demand dedicated server pools.                        |                                                                          |

# Flows

Refer to the `Command-line tool` section below for details on the OMGSERVER ctl tool used in the flows.

## Admin

1. Issue an admin user access token to authorize subsequent server requests.
    - `./omgserversctl.sh admin useCredentials <user> <password>`

## Support

1. Issue a support user access token to authorize subsequent server requests.
    - `./omgserversctl.sh support useCredentials <user> <password>`

### Create and configure a new tenant

1. Create a new tenant for a game developer.
    - `./omgserversctl.sh support createTenant`

1. Assign a human-readable alias to the new tenant.
    - `./omgserversctl.sh support createTenantAlias <tenant_id> <alias>`

1. Create a new game developer account.
    - `./omgserversctl.sh support createDeveloper`

1. Grant tenant permissions to the developer.
    - `./omgserversctl.sh support createTenantPermission <tenant> <user> <permission>`

## Developers

1. Issue a developer user access token to authorize subsequent server requests.
    - `./omgserversctl.sh developer useCredentials <user> <password>`

### Create a new game project

1. Create a new project within the tenant.
    - `./omgserversctl.sh developer createProject <tenant>`

1. Assign a human-readable alias to the new project.
    - `./omgserversctl.sh developer createProjectAlias <tenant> <project_id> <alias>`

### Deploy a new project version

1. Create a new project version.
    - `./omgserversctl.sh developer createVersion <tenant> <project> <config_path>`

1. Push game runtime Docker image.
    - `docker login -u ${DEVELOPER_USER} -p ${DEVELOPER_PASSWORD} <registry_url>`
    - `docker push http://<registry_url>/omgservers/<tenant_id>/<project_id>/universal:<version_id>`

1. Start version deployment.
    - `./omgserversctl.sh developer deployVersion <tenant> <project> <stage> <version>`

## Players

1. Create a new user for the player.
    - `POST /service/v1/entrypoint/player/request/create-user`

1. Issue a user access token to authorize subsequent server requests.
    - `POST /service/v1/entrypoint/player/request/create-token`

1. Create a new client to interact with a specific game project.
    - `POST /service/v1/entrypoint/player/request/create-client`

1. Communicate with the server asynchronously.
    - `POST /service/v1/entrypoint/player/request/interchange`

## Runtimes

1. Issue a runtime access token to authorize subsequent server requests.
    - `POST /service/v1/entrypoint/runtime/request/create-token`

1. Communicate with the backend asynchronously.
    - `POST /service/v1/entrypoint/runtime/request/interchange`

# Config structure

Each game version must include a configuration that specifies different game modes and custom user data, which will be
accessible to game runtimes within the Docker containers.

```
{
  "modes": [
    {
      "name": "<mode_name>",
      "min_players": <mode_min_players>,
      "max_players": <mode_max_players>,
      "groups": [
        {
          "name": "<mode_group_name>",
          "min_players": <mode_group_min_player>,
          "max_players": <mode_group_max_player>
        }
      ]
    }
  ],
  "user_data": <arbitary_data>
}
```

# Player messages

## Incoming messages

```
{
  "id": "<id>",
  "qualifier": "SERVER_WELCOME_MESSAGE",
  "body": {
    "tenant_id": "<tenant_id>",
    "version_id": "<version_id>",
    "version_created": "<timestamp>"
  }
}
```

```
{
  "id": "<id>",
  "qualifier": "MATCHMAKER_ASSIGNMENT_MESSAGE",
  "body": {
    "matchmaker_id": "<matchmaker_id>"
  }
}
```

```
{
  "id": "<id>",
  "qualifier": "RUNTIME_ASSIGNMENT_MESSAGE",
  "body": {
    "runtime_id": "<runtime_id>",
    "runtime_qualifier": "<runtime_qualifier>",
    "runtime_config": {
      "lobby_config": {
        "lobby_id": "<lobby_id>"
      },
      "match_config": {
        "matchmaker_id": "<matchmaker_id>",
        "match_id": "<match_id>"
      },
      "version_config": <version_config>
    }
  }
}
```

```
{
  "id": "<id>",
  "qualifier": "CONNECTION_UPGRADE_MESSAGE",
  "body": {
    "client_id": "<client_id>",
    "protocol": "<protocol>",
    "web_socket_config": {
      "ws_token": "<ws_token>"
    }
  }
}
```

```
{
  "id": "<id>",
  "qualifier": "DISCONNECTION_REASON_MESSAGE",
  "body": {
    "reason": "<reason>"
  }
}
```

```
{
  "id": "<id>",
  "qualifier": "SERVER_OUTGOING_MESSAGE",
  "body": {
    "message": "<message>"
  }
}
```

## Outgoing messages

```
{
  "id": "<id>",
  "qualifier": "CLIENT_OUTGOING_MESSAGE",
  "body": {
    "data": "<data>"
  }
}
```

# Runtime Commands

## Incoming commands

```
{
  "id": "<id>",
  "qualifier": "INIT_RUNTIME",
  "body": {
    "runtime_config": {
      "lobby_config": {
        "lobby_id": "<lobby_id>"
      },
      "match_config": {
        "matchmaker_id": "<matchmaker_id>",
        "match_id": "<match_id>"
      },
      "version_config": <version_config>
    }
  }
}
```

```
{
  "id": "<id>",
  "qualifier": "ADD_CLIENT",
  "body": {
    "user_id": "<user_id>",
    "client_id": "<client_id>",
    "profile": <profile>
  }
}
```

```
{
  "id": "<id>",
  "qualifier": "ADD_MATCH_CLIENT",
  "body": {
    "user_id": "<user_id>",
    "client_id": "<client_id>",
    "group_name": "<group_name>",
    "profile": <profile>
  }
}
```

```
{
  "id": "<id>",
  "qualifier": "DELETE_CLIENT",
  "body": {
    "client_id": "<client_id>"
  }
}
```

```
{
  "id": "<id>",
  "qualifier": "HANDLE_MESSAGE",
  "body": {
    "client_id": "<client_id>",
    "message": <message>
  }
}
```

## Outgoing commands

```
{
  "qualifier": "RESPOND_CLIENT",
  "body": {
    "client_id": "<client_id>",
    "message": <message>
  }
}
```

```
{
  "qualifier": "SET_PROFILE",
  "body": {
    "client_id": "<client_id>",
    "profile": <profile>
  }
}
```

```
{
  "qualifier": "MULTICAST_MESSAGE",
  "body": {
    "client": [
      "<client_id_1>",
      "<client_id_2>",
      ...
    ],
    "message": <message>
  }
}
```

```
{
  "qualifier": "BROADCAST_MESSAGE",
  "body": {
    "message": <message>
  }
}
```

```
{
  "qualifier": "REQUEST_MATCHMAKING",
  "body": {
    "client_id": "<client_id>",
    "mode": "<mode>"
  }
}
```

```
{
  "qualifier": "KICK_CLIENT",
  "body": {
    "client_id": "<client_id>"
  }
}
```

```
{
  "qualifier": "STOP_MATCHMAKING",
  "body": {
  }
}
```

```
{
  "qualifier": "UPGRADE_CONNECTION",
  "body": {
    "client_id": "<client_id>",
    "protocol": "<protocol>"
  }
}
```


# Command-line tool

```bash
OMGSERVERS ctl, v1.0.0
Usage:
 ./omgserversctl.sh help
 ./omgserversctl.sh logs
 ./omgserversctl.sh environment reset
 ./omgserversctl.sh environment printCurrent
 ./omgserversctl.sh environment printVariable <variable>
 ./omgserversctl.sh environment useEnvironment <name> <service_url>
 ./omgserversctl.sh environment usePublic
 ./omgserversctl.sh environment useLocal
 ./omgserversctl.sh admin useCredentials <user> <password>
 ./omgserversctl.sh admin printCurrent
 ./omgserversctl.sh admin createToken
 ./omgserversctl.sh admin calculateShard <shard_key>
 ./omgserversctl.sh admin generateId
 ./omgserversctl.sh admin bcryptHash <value>
 ./omgserversctl.sh admin pingDockerHost <docker_daemon_uri>
 ./omgserversctl.sh support useCredentials <user> <password>
 ./omgserversctl.sh support printCurrent
 ./omgserversctl.sh support createToken
 ./omgserversctl.sh support createTenant
 ./omgserversctl.sh support createTenantAlias <tenant_id> <alias>
 ./omgserversctl.sh support deleteTenant <tenant>
 ./omgserversctl.sh support createProject <tenant>
 ./omgserversctl.sh support createProjectAlias <tenant> <project_id> <alias>
 ./omgserversctl.sh support deleteProject <tenant> <project>
 ./omgserversctl.sh support createDeveloper
 ./omgserversctl.sh support createTenantPermission <tenant> <user> <permission>
 ./omgserversctl.sh support deleteTenantPermission <tenant> <user> <permission>
 ./omgserversctl.sh support createProjectPermission <tenant> <project> <user> <permission>
 ./omgserversctl.sh support deleteProjectPermission <tenant> <project> <user> <permission>
 ./omgserversctl.sh support createStagePermission <tenant> <project> <stage> <user> <permission>
 ./omgserversctl.sh support deleteStagePermission <tenant> <project> <stage> <user> <permission>
 ./omgserversctl.sh developer useCredentials <user> <password>
 ./omgserversctl.sh developer printCurrent
 ./omgserversctl.sh developer createToken
 ./omgserversctl.sh developer getTenantDetails <tenant>
 ./omgserversctl.sh developer createProject <tenant>
 ./omgserversctl.sh developer createProjectAlias <tenant> <project_id> <alias>
 ./omgserversctl.sh developer getProjectDetails <tenant> <project>
 ./omgserversctl.sh developer deleteProject <tenant> <project>
 ./omgserversctl.sh developer createStage <tenant> <project>
 ./omgserversctl.sh developer createStageAlias <tenant> <stage_id> <alias>
 ./omgserversctl.sh developer getStageDetails <tenant> <project> <stage>
 ./omgserversctl.sh developer deleteStage <tenant> <project> <stage>
 ./omgserversctl.sh developer createVersion <tenant> <project> <config_path>
 ./omgserversctl.sh developer uploadFilesArchive <tenant> <version> <files_directory_path>
 ./omgserversctl.sh developer getVersionDetails <tenant> <version>
 ./omgserversctl.sh developer deleteVersion <tenant> <version>
 ./omgserversctl.sh developer deployVersion <tenant> <project> <stage> <version>
 ./omgserversctl.sh developer getDeploymentDetails <tenant> <deployment>
 ./omgserversctl.sh developer deleteDeployment <tenant> <deployment>
 ./omgserversctl.sh developer createLobbyRequest <tenant> <deployment>
 ./omgserversctl.sh developer deleteLobby <lobby>
 ./omgserversctl.sh developer createMatchmakerRequest <tenant> <deployment>
 ./omgserversctl.sh developer deleteMatchmaker <matchmaker>
```

# Configuration

## Service

- OMGSERVERS_DATABASE_PASSWORD=<db_password>
- OMGSERVERS_BROKER_PASSWORD=<broker_password>
- OMGSERVERS_BUILDER_TOKEN=<builder_token>
- OMGSERVERS_SERVER_X5C=<public_key_string>
- OMGSERVERS_SERVER_SERVICE_USER_PASSWORD=<service_password>
- OMGSERVERS_BOOTSTRAP_ADMIN_USER_PASSWORD=<admin_password>
- OMGSERVERS_BOOTSTRAP_SUPPORT_USER_PASSWORD=<support_password>
- OMGSERVERS_BOOTSTRAP_REGISTRY_USER_PASSWORD=<registry_password>
- OMGSERVERS_BOOTSTRAP_BUILDER_USER_PASSWORD=<builder_password>
- OMGSERVERS_BOOTSTRAP_SERVICE_USER_PASSWORD=<service_password>
- OMGSERVERS_BOOTSTRAP_DISPATCHER_USER_PASSWORD=<dispatcher_password>
- OMGSERVERS_BOOTSTRAP_DEFAULT_POOL_DOCKER_HOSTS_<index>__DOCKER_DAEMON_URI=tcp://<docker_host>:<docker_port>
- OMGSERVERS_BOOTSTRAP_DEFAULT_POOL_DOCKER_HOSTS_<index>__CPU_COUNT=<cpu_count>
- OMGSERVERS_BOOTSTRAP_DEFAULT_POOL_DOCKER_HOSTS_<index>__MEMORY_SIZE=<memory_size>
- OMGSERVERS_BOOTSTRAP_DEFAULT_POOL_DOCKER_HOSTS_<index>__MAX_CONTAINERS=<max_containers>
- OMGSERVERS_APPLICATION_NAME=service
- OMGSERVERS_SERVER_INSTANCE_ID=0
- OMGSERVERS_SERVER_PUBLIC_KEY=file:/jwt_issuer/public_key.pem
- OMGSERVERS_SERVER_PRIVATE_KEY=file:/jwt_issuer/private_key.pem
- OMGSERVERS_SERVER_JWT_ISSUER=omgservers
- OMGSERVERS_SERVER_SERVICE_USER_ALIAS=service
- OMGSERVERS_SERVER_URI=http://gateway:8080
- OMGSERVERS_SERVER_SHARD_COUNT=1
- OMGSERVERS_DATABASE_URL=postgresql://database:5432/omgservers
- OMGSERVERS_DATABASE_USERNAME=omgservers
- OMGSERVERS_BROKER_HOST=broker
- OMGSERVERS_BROKER_PORT=5672
- OMGSERVERS_BROKER_USERNAME=omgservers
- OMGSERVERS_SERVICE_QUEUE=ServiceEvents
- OMGSERVERS_FORWARDING_QUEUE=ForwardedEvents
- OMGSERVERS_BUILDER_URI=http://builder:8080
- OMGSERVERS_BUILDER_USERNAME=omgservers
- OMGSERVERS_DOCKER_CLIENT_TLS_VERIFY=false
- OMGSERVERS_DOCKER_CLIENT_CERT_PATH=/docker/certs
- OMGSERVERS_REGISTRY_URI=http://localhost:5000
- OMGSERVERS_RUNTIMES_DOCKER_NETWORK=omgservers
- OMGSERVERS_RUNTIMES_INACTIVE_INTERVAL=30
- OMGSERVERS_RUNTIMES_OVERRIDING_ENABLED=false
- OMGSERVERS_RUNTIMES_OVERRIDING_URI=http://gateway:8080
- OMGSERVERS_RUNTIMES_DEFAULT_CPU_LIMIT=100
- OMGSERVERS_RUNTIMES_DEFAULT_MEMORY_LIMIT=512
- OMGSERVERS_CLIENTS_TOKEN_LIFETIME=3600
- OMGSERVERS_CLIENTS_INACTIVE_INTERVAL=30
- OMGSERVERS_INITIALIZATION_DATABASE_SCHEMA_ENABLED=true
- OMGSERVERS_INITIALIZATION_DATABASE_SCHEMA_CONCURRENCY=16
- OMGSERVERS_INITIALIZATION_SERVER_INDEX_ENABLED=true
- OMGSERVERS_INITIALIZATION_SERVER_INDEX_SERVERS_<index>=http://gateway:8080
- OMGSERVERS_INITIALIZATION_RELAY_JOB_ENABLED=true
- OMGSERVERS_INITIALIZATION_RELAY_JOB_INTERVAL=1s
- OMGSERVERS_INITIALIZATION_SCHEDULER_JOB_ENABLED=true
- OMGSERVERS_INITIALIZATION_SCHEDULER_JOB_INTERVAL=1s
- OMGSERVERS_INITIALIZATION_BOOTSTRAP_JOB_ENABLED=true
- OMGSERVERS_INITIALIZATION_BOOTSTRAP_JOB_INTERVAL=1s
- OMGSERVERS_BOOTSTRAP_ENABLED=false
- OMGSERVERS_BOOTSTRAP_ADMIN_USER_ALIAS=admin
- OMGSERVERS_BOOTSTRAP_SUPPORT_USER_ALIAS=support
- OMGSERVERS_BOOTSTRAP_REGISTRY_USER_ALIAS=registry
- OMGSERVERS_BOOTSTRAP_BUILDER_USER_ALIAS=builder
- OMGSERVERS_BOOTSTRAP_SERVICE_USER_ALIAS=service
- OMGSERVERS_BOOTSTRAP_DISPATCHER_USER_ALIAS=dispatcher
- OMGSERVERS_BOOTSTRAP_DEFAULT_POOL_ENABLED=true
- OMGSERVERS_LOGGING_ACCESS_LOGS_ENABLED=false
- OMGSERVERS_LOGGING_ROOT_LOGS_LEVEL=INFO
- OMGSERVERS_LOGGING_APP_LOGS_LEVEL=INFO
- OMGSERVERS_LOGGING_TRAFFIC_LOGS_LEVEL=INFO
- OMGSERVERS_LOGGING_CONSOLE_LOGS_ENABLED=true
- OMGSERVERS_OTEL_DISABLED=true
- OMGSERVERS_OTEL_ENDPOINT=http://collector:4317

## Dispatcher

- OMGSERVERS_DISPATCHER_USER_PASSWORD=<dispatcher_password>
- OMGSERVERS_APPLICATION_NAME=dispatcher
- OMGSERVERS_DISPATCHER_USER_ALIAS=dispatcher
- OMGSERVERS_SERVICE_URI=http://service:8080
- OMGSERVERS_SERVER_PUBLIC_KEY=file:/jwt_issuer/public_key.pem
- OMGSERVERS_EXPIRED_CONNECTIONS_HANDLER_JOB_INTERVAL=60s
- OMGSERVERS_REFRESH_DISPATCHER_TOKEN_JOB_INTERVAL=60s
- OMGSERVERS_LOGGING_ACCESS_LOGS_ENABLED=false
- OMGSERVERS_LOGGING_ROOT_LOGS_LEVEL=INFO
- OMGSERVERS_LOGGING_APP_LOGS_LEVEL=INFO
- OMGSERVERS_LOGGING_TRAFFIC_LOGS_LEVEL=INFO
- OMGSERVERS_LOGGING_CONSOLE_LOGS_ENABLED=true
- OMGSERVERS_OTEL_DISABLED=true
- OMGSERVERS_OTEL_ENDPOINT=http://collector:4317
