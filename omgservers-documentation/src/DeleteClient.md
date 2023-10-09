# Client Cleanup

```mermaid
graph TD;

ClientDisconnected(CLIENT_DISCONNECTED) --> deleteClient("deleteClient()")
deleteClient("deleteClient()") --> ClientDeleted(CLIENT_DELETED)
ClientDeleted(CLIENT_DELETED) --> syncMatchmakerCommand("syncMatchmakerCommand(DELETE_CLIENT)")
doKickClient("doKickClient()") --> KickRequested(KICK_REQUESTED)
KickRequested(KICK_REQUESTED) --> syncMatchmakerCommand("syncMatchmakerCommand(DELETE_CLIENT)")

```

