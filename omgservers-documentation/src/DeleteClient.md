# Client Cleanup

```mermaid
graph TD;

ClientDisconnected(CLIENT_DISCONNECTED) --> deleteClient("deleteClient()")
deleteClient("deleteClient()") --> ClientDeleted(CLIENT_DELETED)
ClientDeleted(CLIENT_DELETED) --> syncMatchmakerCommand("syncMatchmakerCommand(DELETE_CLIENT)")
doKickClient("doKickClient()") --> syncMatchmakerCommand("syncMatchmakerCommand(DELETE_CLIENT)")

```

