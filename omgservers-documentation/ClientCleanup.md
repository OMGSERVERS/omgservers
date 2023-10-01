# Client Cleanup

```mermaid
graph TD;

ClientDisconnected(CLIENT_DISCONNECTED) --> deleteClient("deleteClient()")
deleteClient("deleteClient()") --> ClientDeleted(CLIENT_DELETED)
ClientDeleted(CLIENT_DELETED) --> syncMatchmakerCommand("syncMatchmakerCommand(DELETE_CLIENT)")

MatchmakerJob(Job<br/>type: MATCHMAKER) --> viewMatchmakerCommands("viewMatchmakerCommands(NEW)")
viewMatchmakerCommands("viewMatchmakerCommands(NEW)") --> deleteMatchClient("deleteMatchClient()")
deleteMatchClient("deleteMatchClient()") --> MatchClientDeleted(MATCH_CLIENT_DELETED)
MatchClientDeleted(MATCH_CLIENT_DELETED) --> revokeRuntime("revokeRuntime()")
revokeRuntime("revokeRuntime()") --> deleteRuntimeGrant("deleteRuntimeGrant()")
deleteRuntimeGrant("deleteRuntimeGrant()") --> syncDeleteClientRuntimeCommand("syncDeleteClientRuntimeCommand()")

doKickClient("doKickClient()") --> syncMatchmakerCommand("syncMatchmakerCommand(DELETE_CLIENT)")

```

