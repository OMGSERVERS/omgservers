# OMGSERVERS

[![Build](https://github.com/OMGSERVERS/omgservers/actions/workflows/build.yml/badge.svg)](https://github.com/OMGSERVERS/omgservers/actions/workflows/build.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=OMGSERVERS_omgservers&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=OMGSERVERS_omgservers)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=OMGSERVERS_omgservers&metric=bugs)](https://sonarcloud.io/summary/new_code?id=OMGSERVERS_omgservers)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=OMGSERVERS_omgservers&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=OMGSERVERS_omgservers)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=OMGSERVERS_omgservers&metric=coverage)](https://sonarcloud.io/summary/overall?id=OMGSERVERS_omgservers)

OMGSERVERS is a platform for building, testing, and running authoritative servers for online games.

## Core Principles

- Monolithic Architecture.
- Sharding Over Clustering.
- No Vendor Lock-in.

## Features
- Manage developer accounts and permissions.
- Organize projects, versions, and deployments across stages.
- Build Docker images for game runtimes from source code.
- Store and manage player data.
- Match players and launch game runtimes in Docker containers.
- Request/Response API for player authentication and registration.
- Command-based API for delivering asynchronous commands between players and the server.
- WebSockets for enabling two-way communication with game runtimes.
- Defold SDK for implementing the client and server sides of games.
- Headless builds functioning as game runtimes.
- Run game runtime containers on a pool of servers.
- WebSocket Dispatcher to connect game runtimes with players via client WebSocket connections.
- Lobby runtimes to handle player commands and update player data in an authoritative way.
- Match runtimes to allow players to play together.
- Command-line tool for administrative and support tasks.

# Deployment types

## Dedicated

### Standalone
- Single-server service deployment.
- Runs game runtimes locally via the Docker API.

Suitable for:
- Players can be distributed across independent deployments.
- Non-resource-intensive game runtimes.

### Standalone with server pool
- Single-server service deployment.
- Runs game runtime on a pool of Docker hosts.

Suitable for:
- Player can be distributed across independent deployments.
- Resource-intensive game runtimes.

### Sharded
- Distribute data across multiple servers by using shards.
- Support on-demand infrastructure scaling.
- Runs game runtimes locally via the Docker API.

Suitable for:
- A significant number of players should be able to play together.
- Non-resource-intensive game runtimes.

### Sharded with server pool
- Distribute data across multiple servers by using shards.
- Support on-demand infrastructure scaling.
- Runs game runtime on a pool of Docker hosts.

Suitable for:
- A significant number of players should be able to play together.
- Resource-intensive game runtimes.

## Public cloud
- Provide a pay-as-you-go service.
- On-demand dedicated server pools.