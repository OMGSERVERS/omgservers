# OMGSERVERS

[![Build](https://github.com/OMGSERVERS/omgservers/actions/workflows/build.yml/badge.svg)](https://github.com/OMGSERVERS/omgservers/actions/workflows/build.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=OMGSERVERS_omgservers&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=OMGSERVERS_omgservers)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=OMGSERVERS_omgservers&metric=bugs)](https://sonarcloud.io/summary/new_code?id=OMGSERVERS_omgservers)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=OMGSERVERS_omgservers&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=OMGSERVERS_omgservers)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=OMGSERVERS_omgservers&metric=coverage)](https://sonarcloud.io/summary/overall?id=OMGSERVERS_omgservers)

OMGSERVERS is a platform for building, testing, and running authoritative servers for online games.

## Core Principles

- Monolithic Architecture
- Sharding Over Clustering
- No Vendor Lock-in

## Features: Implemented & In-Development

### **Project Management**
- **Developer Management:** Manage developer accounts and permissions.
- **Tenant Management:** Organize projects, versions, and deployments across stages.
- **Registry Integration:** Automatically link uploaded Docker images to project versions.
- **Runtimes Builder:** Build Docker images for game runtimes from source code.

### **Player Management**
- **Player Profiles:** Store and manage player data.
- **Matchmaking:** Match players and launch game runtimes.

### **WebSocket Dispatcher**
- Build game runtimes without web server components.
- Interact with players using client WebSocket connections through the Dispatcher.

### **Player-to-Server Interactions**
- **Player API:** API for creating accounts, sessions, and handling authentication.
- **Command-Based API:** Support asynchronous commands in stateful player sessions.
- **WebSockets:** Enable two-way communication with game runtimes.

### **Game Engine SDKs**
- **Defold Player SDK:** Interact with game runtimes from game.
- **Defold Server SDK:** Build game runtimes using a headless Defold builds.

### **Docker-Based Runtimes**
- **Server Pools:** Run game runtimes across Docker host pools.
- **Lobby Runtimes:** Containerized environments for player preparation.
- **Match Runtimes:** On-demand containers for managing gameplay sessions.

### **Administrative Tools**
- **Command-Line Utility:** Tool for administrative and support tasks.
