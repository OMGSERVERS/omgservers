# Omgservers

```mermaid
graph TD;

MATCHMAKER_CREATED --> syncMatchmakerJob
MATCHMAKER_DELETED --> deleteMatchmakerJob

MATCH_CREATED --> syncRuntime
syncRuntime --> syncMatchJob

MATCH_CLIENT_CREATED --> syncAddClientMatchCommand
syncAddClientMatchCommand --> syncClientRuntime

CLIENT_CREATED --> syncWelcomeMessage
syncWelcomeMessage --> syncClientRuntime
syncClientRuntime --> CLIENT_RUNTIME_CREATED
CLIENT_RUNTIME_CREATED --> syncAssignmentMessage
syncAssignmentMessage --> syncAddClientRuntimeCommand

MATCH_DELETED --> deletedRuntime
deletedRuntime --> deleteMatchJob
deleteMatchJob --> deleteMatchClients
deleteMatchClients --> MATCH_CLIENT_DELETED
MATCH_CLIENT_DELETED --> syncDeleteClientMatchCommand
syncDeleteClientMatchCommand --> deleteClientRuntime
deleteClientRuntime --> CLIENT_RUNTIME_DELETED

CLIENT_DELETED --> deleteClientRuntimes
deleteClientRuntimes --> CLIENT_RUNTIME_DELETED
CLIENT_RUNTIME_DELETED --> deleteRuntimeClient
deleteRuntimeClient --> RUNTIME_CLIENT_DELETED
RUNTIME_CLIENT_DELETED --> syncAddClientRuntimeCommand
deleteClientRuntimes --> syncDeleteClientMatchmakerCommand


```

