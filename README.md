# OMGSERVERS

[![Build](https://github.com/OMGSERVERS/omgservers/actions/workflows/build.yml/badge.svg)](https://github.com/OMGSERVERS/omgservers/actions/workflows/build.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=OMGSERVERS_omgservers&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=OMGSERVERS_omgservers)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=OMGSERVERS_omgservers&metric=bugs)](https://sonarcloud.io/summary/new_code?id=OMGSERVERS_omgservers)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=OMGSERVERS_omgservers&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=OMGSERVERS_omgservers)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=OMGSERVERS_omgservers&metric=coverage)](https://sonarcloud.io/summary/overall?id=OMGSERVERS_omgservers)
![Docker Pulls](https://img.shields.io/docker/pulls/omgservers/service)

OMGSERVERS is a backend for authoritative game servers.

## Principles

- Monolithic Architecture.
- Sharding Over Clustering.
- No Vendor Lock-in.

# Features

- Developer accounts and permissions management.
- Projects, stages, versions, and deployments.
- Authoritative handling of player commands within lobbies.
- Matchmaking and match execution in Docker containers. 
- Player data storage and management.
- Defold SDK for both clients and servers.
- A command-line tool for administrative tasks.

# Flows

## Players
1. **Create a new user for the player**
   - `PUT /service/v1/entrypoint/player/request/create-user`

1. **Issue a user access token to authorize subsequent server requests**
   - `PUT /service/v1/entrypoint/player/request/create-token`

1. **Create a new client for a specific game project**
   - `PUT /service/v1/entrypoint/player/request/create-client`

1. **Communicate with the server asynchronously**
   - `PUT /service/v1/entrypoint/player/request/interchange`

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

# Configuration

## Service

| **Environment Variable**                                                    | **Required** | **Value**                               |
|-----------------------------------------------------------------------------|--------------|-----------------------------------------|
| `OMGSERVERS_DATABASE_PASSWORD`                                              | Yes          | `<db_password>`                         |
| `OMGSERVERS_BROKER_PASSWORD`                                                | Yes          | `<broker_password>`                     |
| `OMGSERVERS_BUILDER_TOKEN`                                                  | Yes          | `<builder_token>`                       |
| `OMGSERVERS_SERVER_X5C`                                                     | Yes          | `<public_key_string>`                   |
| `OMGSERVERS_SERVER_SERVICE_USER_PASSWORD`                                   | Yes          | `<service_password>`                    |
| `OMGSERVERS_BOOTSTRAP_ADMIN_USER_PASSWORD`                                  | Yes          | `<admin_password>`                      |
| `OMGSERVERS_BOOTSTRAP_SUPPORT_USER_PASSWORD`                                | Yes          | `<support_password>`                    |
| `OMGSERVERS_BOOTSTRAP_REGISTRY_USER_PASSWORD`                               | Yes          | `<registry_password>`                   |
| `OMGSERVERS_BOOTSTRAP_BUILDER_USER_PASSWORD`                                | Yes          | `<builder_password>`                    |
| `OMGSERVERS_BOOTSTRAP_SERVICE_USER_PASSWORD`                                | Yes          | `<service_password>`                    |
| `OMGSERVERS_BOOTSTRAP_DISPATCHER_USER_PASSWORD`                             | Yes          | `<dispatcher_password>`                 |
| `OMGSERVERS_BOOTSTRAP_DEFAULT_POOL_DOCKER_HOSTS_<index>__DOCKER_DAEMON_URI` | Yes          | `tcp://<docker_host>:<docker_port>`     |
| `OMGSERVERS_BOOTSTRAP_DEFAULT_POOL_DOCKER_HOSTS_<index>__CPU_COUNT`         | Yes          | `<cpu_count>`                           |
| `OMGSERVERS_BOOTSTRAP_DEFAULT_POOL_DOCKER_HOSTS_<index>__MEMORY_SIZE`       | Yes          | `<memory_size>`                         |
| `OMGSERVERS_BOOTSTRAP_DEFAULT_POOL_DOCKER_HOSTS_<index>__MAX_CONTAINERS`    | Yes          | `<max_containers>`                      |
| `OMGSERVERS_APPLICATION_NAME`                                               | No           | `service`                               |
| `OMGSERVERS_SERVER_INSTANCE_ID`                                             | No           | `0`                                     |
| `OMGSERVERS_SERVER_PUBLIC_KEY`                                              | No           | `file:/jwt_issuer/public_key.pem`       |
| `OMGSERVERS_SERVER_PRIVATE_KEY`                                             | No           | `file:/jwt_issuer/private_key.pem`      |
| `OMGSERVERS_SERVER_JWT_ISSUER`                                              | No           | `omgservers`                            |
| `OMGSERVERS_SERVER_SERVICE_USER_ALIAS`                                      | No           | `service`                               |
| `OMGSERVERS_SERVER_URI`                                                     | No           | `http://gateway:8080`                   |
| `OMGSERVERS_SERVER_SHARD_COUNT`                                             | No           | `1`                                     |
| `OMGSERVERS_DATABASE_URL`                                                   | No           | `postgresql://database:5432/omgservers` |
| `OMGSERVERS_DATABASE_USERNAME`                                              | No           | `omgservers`                            |
| `OMGSERVERS_BROKER_HOST`                                                    | No           | `broker`                                |
| `OMGSERVERS_BROKER_PORT`                                                    | No           | `5672`                                  |
| `OMGSERVERS_BROKER_USERNAME`                                                | No           | `omgservers`                            |
| `OMGSERVERS_SERVICE_QUEUE`                                                  | No           | `ServiceEvents`                         |
| `OMGSERVERS_FORWARDING_QUEUE`                                               | No           | `ForwardedEvents`                       |
| `OMGSERVERS_BUILDER_URI`                                                    | No           | `http://builder:8080`                   |
| `OMGSERVERS_BUILDER_USERNAME`                                               | No           | `omgservers`                            |
| `OMGSERVERS_DOCKER_CLIENT_TLS_VERIFY`                                       | No           | `false`                                 |
| `OMGSERVERS_DOCKER_CLIENT_CERT_PATH`                                        | No           | `/docker/certs`                         |
| `OMGSERVERS_REGISTRY_URI`                                                   | No           | `http://localhost:5000`                 |
| `OMGSERVERS_RUNTIMES_DOCKER_NETWORK`                                        | No           | `omgservers`                            |
| `OMGSERVERS_RUNTIMES_INACTIVE_INTERVAL`                                     | No           | `30`                                    |
| `OMGSERVERS_RUNTIMES_OVERRIDING_ENABLED`                                    | No           | `false`                                 |
| `OMGSERVERS_RUNTIMES_OVERRIDING_URI`                                        | No           | `http://gateway:8080`                   |
| `OMGSERVERS_RUNTIMES_DEFAULT_CPU_LIMIT`                                     | No           | `100`                                   |
| `OMGSERVERS_RUNTIMES_DEFAULT_MEMORY_LIMIT`                                  | No           | `512`                                   |
| `OMGSERVERS_CLIENTS_TOKEN_LIFETIME`                                         | No           | `3600`                                  |
| `OMGSERVERS_CLIENTS_INACTIVE_INTERVAL`                                      | No           | `30`                                    |
| `OMGSERVERS_INITIALIZATION_DATABASE_SCHEMA_ENABLED`                         | No           | `true`                                  |
| `OMGSERVERS_INITIALIZATION_DATABASE_SCHEMA_CONCURRENCY`                     | No           | `16`                                    |
| `OMGSERVERS_INITIALIZATION_SERVER_INDEX_ENABLED`                            | No           | `true`                                  |
| `OMGSERVERS_INITIALIZATION_SERVER_INDEX_SERVERS_<index>`                    | No           | `http://gateway:8080`                   |
| `OMGSERVERS_INITIALIZATION_RELAY_JOB_ENABLED`                               | No           | `true`                                  |
| `OMGSERVERS_INITIALIZATION_RELAY_JOB_INTERVAL`                              | No           | `1s`                                    |
| `OMGSERVERS_INITIALIZATION_SCHEDULER_JOB_ENABLED`                           | No           | `true`                                  |
| `OMGSERVERS_INITIALIZATION_SCHEDULER_JOB_INTERVAL`                          | No           | `1s`                                    |
| `OMGSERVERS_INITIALIZATION_BOOTSTRAP_JOB_ENABLED`                           | No           | `true`                                  |
| `OMGSERVERS_INITIALIZATION_BOOTSTRAP_JOB_INTERVAL`                          | No           | `1s`                                    |
| `OMGSERVERS_BOOTSTRAP_ENABLED`                                              | No           | `false`                                 |
| `OMGSERVERS_BOOTSTRAP_ADMIN_USER_ALIAS`                                     | No           | `admin`                                 |
| `OMGSERVERS_BOOTSTRAP_SUPPORT_USER_ALIAS`                                   | No           | `support`                               |
| `OMGSERVERS_BOOTSTRAP_REGISTRY_USER_ALIAS`                                  | No           | `registry`                              |
| `OMGSERVERS_BOOTSTRAP_BUILDER_USER_ALIAS`                                   | No           | `builder`                               |
| `OMGSERVERS_BOOTSTRAP_SERVICE_USER_ALIAS`                                   | No           | `service`                               |
| `OMGSERVERS_BOOTSTRAP_DISPATCHER_USER_ALIAS`                                | No           | `dispatcher`                            |
| `OMGSERVERS_BOOTSTRAP_DEFAULT_POOL_ENABLED`                                 | No           | `true`                                  |
| `OMGSERVERS_LOGGING_ACCESS_LOGS_ENABLED`                                    | No           | `false`                                 |
| `OMGSERVERS_LOGGING_ROOT_LOGS_LEVEL`                                        | No           | `INFO`                                  |
| `OMGSERVERS_LOGGING_APP_LOGS_LEVEL`                                         | No           | `INFO`                                  |
| `OMGSERVERS_LOGGING_TRAFFIC_LOGS_LEVEL`                                     | No           | `INFO`                                  |
| `OMGSERVERS_LOGGING_CONSOLE_LOGS_ENABLED`                                   | No           | `true`                                  |
| `OMGSERVERS_OTEL_DISABLED`                                                  | No           | `true`                                  |
| `OMGSERVERS_OTEL_ENDPOINT`                                                  | No           | `http://collector:4317`                 |

## Dispatcher

| **Environment Variable**                                     | **Required** | **Value**                         |
|--------------------------------------------------------------|--------------|-----------------------------------|
| `OMGSERVERS_DISPATCHER_USER_PASSWORD`                        | Yes          | `<dispatcher_password>`           |
| `OMGSERVERS_APPLICATION_NAME`                                | No           | `dispatcher`                      |
| `OMGSERVERS_DISPATCHER_USER_ALIAS`                           | No           | `dispatcher`                      |
| `OMGSERVERS_SERVICE_URI`                                     | No           | `http://service:8080`             |
| `OMGSERVERS_SERVER_PUBLIC_KEY`                               | No           | `file:/jwt_issuer/public_key.pem` |
| `OMGSERVERS_EXPIRED_CONNECTIONS_HANDLER_JOB_INTERVAL`        | No           | `60s`                             |
| `OMGSERVERS_REFRESH_DISPATCHER_TOKEN_JOB_INTERVAL`           | No           | `60s`                             |
| `OMGSERVERS_LOGGING_ACCESS_LOGS_ENABLED`                     | No           | `false`                           |
| `OMGSERVERS_LOGGING_ROOT_LOGS_LEVEL`                         | No           | `INFO`                            |
| `OMGSERVERS_LOGGING_APP_LOGS_LEVEL`                          | No           | `INFO`                            |
| `OMGSERVERS_LOGGING_TRAFFIC_LOGS_LEVEL`                      | No           | `INFO`                            |
| `OMGSERVERS_LOGGING_CONSOLE_LOGS_ENABLED`                    | No           | `true`                            |
| `OMGSERVERS_OTEL_DISABLED`                                   | No           | `true`                            |
| `OMGSERVERS_OTEL_ENDPOINT`                                   | No           | `http://collector:4317`           |
