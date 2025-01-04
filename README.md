# OMGSERVERS

[![Build](https://github.com/OMGSERVERS/omgservers/actions/workflows/build.yml/badge.svg)](https://github.com/OMGSERVERS/omgservers/actions/workflows/build.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=OMGSERVERS_omgservers&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=OMGSERVERS_omgservers)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=OMGSERVERS_omgservers&metric=bugs)](https://sonarcloud.io/summary/new_code?id=OMGSERVERS_omgservers)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=OMGSERVERS_omgservers&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=OMGSERVERS_omgservers)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=OMGSERVERS_omgservers&metric=coverage)](https://sonarcloud.io/summary/overall?id=OMGSERVERS_omgservers)

OMGSERVERS is a platform for building, testing, and running authoritative servers for multiplayer games.

## Core Principles

- No Vendor Lock-in
- Monolithic Architecture
- Sharding Over Clustering

## Features: Implemented & In-Development

### **Developer & Tenant Management**
- **Developer Account Management:** Manage developer accounts and permissions.
- **Tenant & Project Management:** Oversee tenants, projects, and version deployments across stages.

### **Player Management**
- **Player Profile Management:** Store and manage player states, progress, and profiles.
- **Matchmaking System:** Match players and launch on-demand game runtimes for gameplay.
- **Player API:** Provide APIs for account creation and authentication.

### **WebSocket Dispatcher**
- Build game runtimes without web server components using client WebSocket connections to the Dispatcher.

### **Player-to-Server Interactions**
- **Command-Based API:** Support asynchronous commands in stateful player sessions.
- **WebSockets:** Enable two-way communication between players and game runtimes.

### **Game Engine SDKs**
- **Defold Player SDK:** Enables interaction with game runtimes.
- **Defold Server SDK:** Supports building game runtimes using a headless Defold build.

### **Docker-Based Runtimes**
- **Lobbies:** Containerized environments for player preparation before matches.
- **Matches:** On-demand containers to manage gameplay sessions and interactions.

### **Build & Deployment**
- **Automated Version Linking:** Automatically link uploaded Docker images to project versions.
- **Runtime Image Builder:** Build Docker images for game runtimes from source code.
- **Version Staging:** Distribute project versions across stages for testing and release.

### **Administrative Tools**
- **Command-Line Utility:** Tool for administrative and support tasks.  
