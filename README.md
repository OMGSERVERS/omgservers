# OMGSERVERS

[![Build](https://github.com/OMGSERVERS/omgservers/actions/workflows/build.yml/badge.svg)](https://github.com/OMGSERVERS/omgservers/actions/workflows/build.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=OMGSERVERS_omgservers&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=OMGSERVERS_omgservers)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=OMGSERVERS_omgservers&metric=bugs)](https://sonarcloud.io/summary/new_code?id=OMGSERVERS_omgservers)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=OMGSERVERS_omgservers&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=OMGSERVERS_omgservers)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=OMGSERVERS_omgservers&metric=coverage)](https://sonarcloud.io/summary/overall?id=OMGSERVERS_omgservers)
![Docker Pulls](https://img.shields.io/docker/pulls/omgservers/service)

OMGSERVERS is a backend for authoritative game servers.

## How to test it

```bash
curl -L https://github.com/OMGSERVERS/omgservers/releases/download/0.6.0/install.sh | bash
```

# Features

- Tenants, projects, stages, versions and deployments
- Developer account and permission management
- Running lobbies and matches in Docker containers
- Integrated Docker registry for images
- Scheduler for distributing containers across servers
- Player and game runtime lifecycle management
- Asynchronous APIs for backend interactions
- WebSocket dispatcher for player and server connections
- Game engine support: [Defold SDK](https://github.com/OMGSERVERS/omgdefold)
- CLI tool for administrative and development tasks
- Multiple installation options: standalone, sharded, ha cluster, cloud