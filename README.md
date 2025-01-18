# OMGSERVERS

[![Build](https://github.com/OMGSERVERS/omgservers/actions/workflows/build.yml/badge.svg)](https://github.com/OMGSERVERS/omgservers/actions/workflows/build.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=OMGSERVERS_omgservers&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=OMGSERVERS_omgservers)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=OMGSERVERS_omgservers&metric=bugs)](https://sonarcloud.io/summary/new_code?id=OMGSERVERS_omgservers)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=OMGSERVERS_omgservers&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=OMGSERVERS_omgservers)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=OMGSERVERS_omgservers&metric=coverage)](https://sonarcloud.io/summary/overall?id=OMGSERVERS_omgservers)

OMGSERVERS is a backend for authoritative game servers.

## Principles

- Monolithic Architecture.
- Sharding Over Clustering.
- No Vendor Lock-in.

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

| **Type**                        | **Description**                                                     | **Suitable for**                                                         |
|---------------------------------|---------------------------------------------------------------------|--------------------------------------------------------------------------|
| **Standalone**                  | Single-server service installation.                                 | It is acceptable to distribute players across independent installations. |
|                                 | Game runtimes are executed on the same server using the Docker API. | Non-resource-intensive game runtimes.                                    |
| **Standalone with server pool** | Single-server service installation.                                 | It is acceptable to distribute players across independent installations. |
|                                 | Run game runtime on a pool of Docker hosts.                         | Resource-intensive game runtimes.                                        |
| **Sharded**                     | Distribute data across multiple servers by using shards.            | A significant number of players should be able to play together.         |
|                                 | Support on-demand infrastructure scaling.                           | Non-resource-intensive game runtimes.                                    |
|                                 | Servers are shared for both service and game runtime execution.     |                                                                          |
| **Sharded with server pool**    | Distribute data across multiple servers by using shards.            | A significant number of players should be able to play together.         |
|                                 | Support on-demand infrastructure scaling.                           | Resource-intensive game runtimes.                                        |
|                                 | Run game runtime on a pool of Docker hosts.                         |                                                                          |
| **Public cloud**                | Provide a pay-as-you-go service.                                    |                                                                          |
|                                 | On-demand dedicated server pools.                                   |                                                                          |

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
