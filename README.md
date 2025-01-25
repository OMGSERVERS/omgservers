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
- Organization of tenants, projects, stages, versions, and deployments.
- Two types of game runtimes: lobbies and matches.
- Game runtimes execution in Docker containers.
- Players assignment across lobbies and matches.
- Players data storage and management.
- [Defold SDK](https://github.com/OMGSERVERS/omgdefold) support for both clients and servers.
- A command-line tool for administrative tasks.

# User Roles

- Admin – Manages backend installation.
- Support – Creates tenants for developers.
- Developer – Push game runtime docker images.
- Player – Plays games developed by developers.
- Runtime – Interacts with the backend and handles gameplay.

# How it Works

- A support user creates a new tenant for the developer to use.
- The developer creates and sets up a new project within the tenant.
- The developer creates a new version of the project.
- The developer pushes game runtime Docker images to the registry under the created version.
- The developer initiates the deployment of the new version.
- When the first players connect, the backend starts and assigns lobbies to them within the deployment.
- To enable multiplayer gameplay, the backend runs matches.
- Lobbies and matches use the OMGSERVER SDK to execute backend-specific commands.
- Players use the OMGPLAYER SDK to interact with the backend.
- All incoming player messages are routed to their assigned runtimes.
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

# Guide to Performing Typical Flows

It shows low-level interaction via the backend API, but the same functionality can also be achieved using the OMGSERVERS
CLI, OMGPLAYER SDK or OMGSERVER SDK.

## Create and configure a new tenant

1. Issue an access token: `POST /service/v1/entrypoint/support/request/create-token`
1. Create a new tenant: `POST /service/v1/entrypoint/support/request/create-tenant`
1. Assign an alias: `POST /service/v1/entrypoint/support/request/create-tenant-alias`
1. Create a new developer account: `POST /service/v1/entrypoint/support/request/create-developer`
1. Grant permissions: `POST /service/v1/entrypoint/support/request/create-tenant-permissions`

## Create a new game project

1. Issue an access token: `POST /service/v1/entrypoint/developer/request/create-token`
1. Create a new project: `POST /service/v1/entrypoint/developer/request/create-project`
1. Assign an alias: `POST /service/v1/entrypoint/developer/request/create-project-alias`

## Deploy a new project version

1. Create a new version: `POST /service/v1/entrypoint/developer/request/create-version`
1. Push game runtime Docker image.
    - `docker login -u ${DEVELOPER_USER} -p ${DEVELOPER_PASSWORD} <registry_url>`
    - `docker push http://<registry_url>/omgservers/<tenant_id>/<project_id>/universal:<version_id>`
1. Start deployment: `POST /service/v1/entrypoint/developer/request/deploy-version`

## Player message interchange

1. Create a new user: `POST /service/v1/entrypoint/player/request/create-user`
1. Issue an access token: `POST /service/v1/entrypoint/player/request/create-token`
1. Create a new client: `POST /service/v1/entrypoint/player/request/create-client`
1. Communicate with the server: `POST /service/v1/entrypoint/player/request/interchange`
    - [The format of incoming and outgoing messages](https://github.com/OMGSERVERS/omgservers/blob/main/.techdocs/player_messages.md)

## Runtime command interchange

1. Issue an access token: `POST /service/v1/entrypoint/runtime/request/create-token`
1. Communicate with the backend: `POST /service/v1/entrypoint/runtime/request/interchange`
    - [The format of incoming and outgoing commands](https://github.com/OMGSERVERS/omgservers/blob/main/.techdocs/runtime_commands.md)
