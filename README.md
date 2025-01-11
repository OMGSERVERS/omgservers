# OMGSERVERS

[![Build](https://github.com/OMGSERVERS/omgservers/actions/workflows/build.yml/badge.svg)](https://github.com/OMGSERVERS/omgservers/actions/workflows/build.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=OMGSERVERS_omgservers&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=OMGSERVERS_omgservers)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=OMGSERVERS_omgservers&metric=bugs)](https://sonarcloud.io/summary/new_code?id=OMGSERVERS_omgservers)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=OMGSERVERS_omgservers&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=OMGSERVERS_omgservers)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=OMGSERVERS_omgservers&metric=coverage)](https://sonarcloud.io/summary/overall?id=OMGSERVERS_omgservers)

OMGSERVERS is a backend for authoritative game servers.

# Features

- Manage developer accounts and permissions.
- Organize projects, versions, and deployments across stages.
- Build Docker images for game runtimes from source code.
- Store and manage player data.
- Match players and launch game runtimes in Docker containers.
- Request/Response API for player authentication and registration.
- Command-based API for delivering asynchronous commands between players and the server.
- WebSockets for enabling two-way communication with game runtimes.
- Defold SDK for implementing the client and server sides of games.
- Run game runtime containers on a pool of servers.
- WebSocket Dispatcher to connect game runtimes with players via client WebSocket connections.
- Lobby runtimes to handle player commands and update player data in an authoritative way.
- Match runtimes to allow players to play together.
- Command-line tool for administrative and support tasks.

# Installation Types

| **Type**                        | **Description**                                          | **Suitable for**                                                 |
|---------------------------------|----------------------------------------------------------|------------------------------------------------------------------|
| **Standalone**                  | Single-server service installation.                      | Players can be distributed across independent installations.     |
|                                 | Run game runtimes locally via the Docker API.            | Non-resource-intensive game runtimes.                            |
| **Standalone with server pool** | Single-server service installation.                      | Players can be distributed across independent installations.     |
|                                 | Run game runtime on a pool of Docker hosts.              | Resource-intensive game runtimes.                                |
| **Sharded**                     | Distribute data across multiple servers by using shards. | A significant number of players should be able to play together. |
|                                 | Support on-demand infrastructure scaling.                | Non-resource-intensive game runtimes.                            |
|                                 | Run game runtimes locally via the Docker API.            |                                                                  |
| **Sharded with server pool**    | Distribute data across multiple servers by using shards. | A significant number of players should be able to play together. |
|                                 | Support on-demand infrastructure scaling.                | Resource-intensive game runtimes.                                |
|                                 | Run game runtime on a pool of Docker hosts.              |                                                                  |
| **Public cloud**                | Provide a pay-as-you-go service.                         |                                                                  |
|                                 | On-demand dedicated server pools.                        |                                                                  |

# Configuration

## Service

### Mandatory

| Environment Variable                                                                                             |
|------------------------------------------------------------------------------------------------------------------|
| OMGSERVERS_DATABASE_PASSWORD: `<db_password>`                                                                    |
| OMGSERVERS_BROKER_PASSWORD: `<broker_password>`                                                                  |
| OMGSERVERS_BUILDER_TOKEN: `<builder_token>`                                                                      |
| OMGSERVERS_SERVER_X5C: `<public_key_string>`                                                                     |
| OMGSERVERS_SERVER_SERVICE_USER_PASSWORD: `<service_password>`                                                    |
| OMGSERVERS_BOOTSTRAP_ADMIN_USER_PASSWORD: `<admin_password>`                                                     |
| OMGSERVERS_BOOTSTRAP_SUPPORT_USER_PASSWORD: `<support_password>`                                                 |
| OMGSERVERS_BOOTSTRAP_REGISTRY_USER_PASSWORD: `<registry_password>`                                               |
| OMGSERVERS_BOOTSTRAP_BUILDER_USER_PASSWORD: `<builder_password>`                                                 |
| OMGSERVERS_BOOTSTRAP_SERVICE_USER_PASSWORD: `<service_password>`                                                 |
| OMGSERVERS_BOOTSTRAP_DISPATCHER_USER_PASSWORD: `<dispatcher_password>`                                           |
| OMGSERVERS_BOOTSTRAP_DEFAULT_POOL_DOCKER_HOSTS_`<index>`__DOCKER_DAEMON_URI: `tcp://<docker_host>:<docker_port>` |
| OMGSERVERS_BOOTSTRAP_DEFAULT_POOL_DOCKER_HOSTS_`<index>`__CPU_COUNT: `<cpu_count>`                               |
| OMGSERVERS_BOOTSTRAP_DEFAULT_POOL_DOCKER_HOSTS_`<index>`__MEMORY_SIZE: `<memory_size>`                           |
| OMGSERVERS_BOOTSTRAP_DEFAULT_POOL_DOCKER_HOSTS_`<index>`__MAX_CONTAINERS: `<max_containers>`                     |

### Optional

| Environment Variable                                                            |
|---------------------------------------------------------------------------------|
| OMGSERVERS_APPLICATION_NAME: `service`                                          |
| OMGSERVERS_SERVER_INSTANCE_ID: `0`                                              |
| OMGSERVERS_SERVER_PUBLIC_KEY: `file:/jwt_issuer/public_key.pem`                 |
| OMGSERVERS_SERVER_PRIVATE_KEY: `file:/jwt_issuer/private_key.pem`               |
| OMGSERVERS_SERVER_JWT_ISSUER: `omgservers`                                      |
| OMGSERVERS_SERVER_SERVICE_USER_ALIAS: `service`                                 |
| OMGSERVERS_INDEX_SERVER_URI: `http://gateway:8080`                              |
| OMGSERVERS_INDEX_SHARD_COUNT: `1`                                               |
| OMGSERVERS_DATABASE_URL: `postgresql://database:5432/omgservers`                |
| OMGSERVERS_DATABASE_USERNAME: `omgservers`                                      |
| OMGSERVERS_BROKER_HOST: `broker`                                                |
| OMGSERVERS_BROKER_PORT: `5672`                                                  |
| OMGSERVERS_BROKER_USERNAME: `omgservers`                                        |
| OMGSERVERS_BUILDER_URI: `http://builder:8080`                                   |
| OMGSERVERS_BUILDER_USERNAME: `omgservers`                                       |
| OMGSERVERS_SERVICE_QUEUE: `ServiceEvents`                                       |
| OMGSERVERS_FORWARDING_QUEUE: `ForwardedEvents`                                  |
| OMGSERVERS_DOCKER_CLIENT_TLS_VERIFY: `false`                                    |
| OMGSERVERS_DOCKER_CLIENT_CERT_PATH: `/docker/certs`                             |
| OMGSERVERS_REGISTRY_URI: `http://localhost:5000`                                |
| OMGSERVERS_RUNTIMES_DOCKER_NETWORK: `omgservers`                                |
| OMGSERVERS_RUNTIMES_INACTIVE_INTERVAL: `30`                                     |
| OMGSERVERS_RUNTIMES_DEFAULT_CPU_LIMIT: `1`                                      |
| OMGSERVERS_RUNTIMES_DEFAULT_MEMORY_LIMIT: `512`                                 |
| OMGSERVERS_CLIENTS_TOKEN_LIFETIME: `3600`                                       |
| OMGSERVERS_CLIENTS_INACTIVE_INTERVAL: `30`                                      |
| OMGSERVERS_INITIALIZATION_DATABASE_SCHEMA_ENABLED: `true`                       |
| OMGSERVERS_INITIALIZATION_DATABASE_SCHEMA_CONCURRENCY: `16`                     |
| OMGSERVERS_INITIALIZATION_SERVER_INDEX_ENABLED: `true`                          |
| OMGSERVERS_INITIALIZATION_SERVER_INDEX_SERVERS_`<index>`: `http://gateway:8080` |
| OMGSERVERS_INITIALIZATION_RELAY_JOB_ENABLED: `true`                             |
| OMGSERVERS_INITIALIZATION_RELAY_JOB_INTERVAL: `1s`                              |
| OMGSERVERS_INITIALIZATION_SCHEDULER_JOB_ENABLED: `true`                         |
| OMGSERVERS_INITIALIZATION_SCHEDULER_JOB_INTERVAL: `1s`                          |
| OMGSERVERS_INITIALIZATION_BOOTSTRAP_JOB_ENABLED: `true`                         |
| OMGSERVERS_INITIALIZATION_BOOTSTRAP_JOB_INTERVAL: `1s`                          |
| OMGSERVERS_BOOTSTRAP_ENABLED: `true`                                            |
| OMGSERVERS_BOOTSTRAP_ADMIN_USER_ALIAS: `admin`                                  |
| OMGSERVERS_BOOTSTRAP_SUPPORT_USER_ALIAS: `support`                              |
| OMGSERVERS_BOOTSTRAP_REGISTRY_USER_ALIAS: `registry`                            |
| OMGSERVERS_BOOTSTRAP_BUILDER_USER_ALIAS: `builder`                              |
| OMGSERVERS_BOOTSTRAP_SERVICE_USER_ALIAS: `service`                              |
| OMGSERVERS_BOOTSTRAP_DISPATCHER_USER_ALIAS: `dispatcher`                        |
| OMGSERVERS_BOOTSTRAP_DEFAULT_POOL_ENABLED: `true`                               |
| OMGSERVERS_LOGGING_ACCESS_LOGS_ENABLED: `false`                                 |
| OMGSERVERS_LOGGING_ROOT_LOGS_LEVEL: `INFO`                                      |
| OMGSERVERS_LOGGING_APP_LOGS_LEVEL: `INFO`                                       |
| OMGSERVERS_LOGGING_TRAFFIC_LOGS_LEVEL: `INFO`                                   |
| OMGSERVERS_LOGGING_CONSOLE_LOGS_ENABLED: `true`                                 |
| OMGSERVERS_OTEL_DISABLED: `true`                                                |
| OMGSERVERS_OTEL_ENDPOINT: `http://localhost:4317`                               |

## Dispatcher

### Mandatory

| Environment Variable                                         |
|--------------------------------------------------------------|
| OMGSERVERS_DISPATCHER_USER_PASSWORD: `<dispatcher_password>` |

### Optional

| Environment Variable                                            |
|-----------------------------------------------------------------|
| OMGSERVERS_APPLICATION_NAME: `dispatcher`                       |
| OMGSERVERS_DISPATCHER_USER_ALIAS: `dispatcher`                  |
| OMGSERVERS_SERVICE_URI: `http://service:8080`                   |
| OMGSERVERS_SERVER_PUBLIC_KEY: `file:/jwt_issuer/public_key.pem` |
| OMGSERVERS_EXPIRED_CONNECTIONS_HANDLER_JOB_INTERVAL: `60s`      |
| OMGSERVERS_REFRESH_DISPATCHER_TOKEN_JOB_INTERVAL: `60s`         |
| OMGSERVERS_LOGGING_ACCESS_LOGS_ENABLED: `false`                 |
| OMGSERVERS_LOGGING_ROOT_LOGS_LEVEL: `INFO`                      |
| OMGSERVERS_LOGGING_APP_LOGS_LEVEL: `INFO`                       |
| OMGSERVERS_LOGGING_TRAFFIC_LOGS_LEVEL: `INFO`                   |
| OMGSERVERS_LOGGING_CONSOLE_LOGS_ENABLED: `true`                 |
| OMGSERVERS_OTEL_DISABLED: `true`                                |
| OMGSERVERS_OTEL_ENDPOINT: `http://collector:4317`               |

# Development

## Technology stack

- Java, Quarkus, Postgresql, ActiveMQ, Docker, Bash

## Principles

- Monolithic Architecture.
- Sharding Over Clustering.
- No Vendor Lock-in.
