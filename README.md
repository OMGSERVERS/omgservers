# OMGSERVERS

[![Build](https://github.com/OMGSERVERS/omgservers/actions/workflows/build.yml/badge.svg)](https://github.com/OMGSERVERS/omgservers/actions/workflows/build.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=OMGSERVERS_omgservers&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=OMGSERVERS_omgservers)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=OMGSERVERS_omgservers&metric=bugs)](https://sonarcloud.io/summary/new_code?id=OMGSERVERS_omgservers)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=OMGSERVERS_omgservers&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=OMGSERVERS_omgservers)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=OMGSERVERS_omgservers&metric=coverage)](https://sonarcloud.io/summary/overall?id=OMGSERVERS_omgservers)

OMGSERVERS is a platform for building, testing, and running multiplayer game servers, with a focus on cost-efficiency 
and vendor independence.

## Core Principles

- **No Vendor Lock-in**  
  Designed to minimize dependencies on any specific vendor, allowing teams to reduce costs and maintain agility
  in server operations.

- **Simplified Monolithic Architecture**  
  A monolithic approach is prioritized to make development and maintenance easier for small teams.

- **Sharding Over Clustering**  
  Sharding is adopted instead of clustering to simplify maintenance and enable linear scalability in throughput.

## Currently Implemented / In-Development Features

- **Developer Management**  
  Manage users and permissions for development teams.

- **Tenant Management**  
  Organize tenants, projects, stages, versions, and deployments.

- **Docker Registry Integration**  
  Automatically populate versions when Docker images are loaded.

- **Builder**  
  Build Docker images directly from source code.

- **Straightforward Matchmaking**  
  Match players based on minimum/maximum players per match or group.

- **Game Runtimes**  
  Support lobbies and matches to facilitate game sessions.

- **Server Pool for Game Runtimes**  
  Implementation a pool of servers to run game runtimes via the Docker API.

- **Defold SDK (Client/Server Side)**  
  Provide SDK support for game and runtime development using the Defold engine.

- **Command-Like API for Game Runtimes**  
  Enable command-based interactions for managing game runtimes.

- **Request/Response Protocol via APIs**  
  Facilitate player creation, authentication, and data exchange through secure APIs.

- **Player Profiles**  
  Store and manage player states and progress.

- **Dispatcher for Game Runtime Integration**  
  Integrate game runtimes with player sessions using client websocket connections.

- **CTL (Control Tool)**  
  Provide a command-line tool for admin and support operations.
