# OMGSERVERS

[![Build](https://github.com/OMGSERVERS/omgservers/actions/workflows/build.yml/badge.svg)](https://github.com/OMGSERVERS/omgservers/actions/workflows/build.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=OMGSERVERS_omgservers&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=OMGSERVERS_omgservers)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=OMGSERVERS_omgservers&metric=bugs)](https://sonarcloud.io/summary/new_code?id=OMGSERVERS_omgservers)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=OMGSERVERS_omgservers&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=OMGSERVERS_omgservers)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=OMGSERVERS_omgservers&metric=coverage)](https://sonarcloud.io/summary/overall?id=OMGSERVERS_omgservers)
![Docker Pulls](https://img.shields.io/docker/pulls/omgservers/service)

OMGSERVERS is a backend for authoritative game servers.

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
- The developer initiates the fetchDeploymentResult of the new version.
- When the first players connect, the backend starts and assigns lobbies to them within the fetchDeploymentResult.
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
