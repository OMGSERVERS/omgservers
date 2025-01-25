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
