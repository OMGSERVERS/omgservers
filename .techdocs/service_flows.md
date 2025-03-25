# Client created

- Player creates Client using PlayerApi, it triggers CLIENT_CREATED
- Handler of CLIENT_CREATED creates DeploymentRequest for DeploymentTask
- Deployment task handles DeploymentRequest entities
    - It creates DeploymentLobbyResource, it triggers DEPLOYMENT_LOBBY_RESOURCE_CREATED
    - It creates DeploymentMatchmakerResource, it triggers DEPLOYMENT_MATCHMAKER_RESOURCE_CREATED

- Handle of DEPLOYMENT_LOBBY_RESOURCE_CREATED creates Lobby using LobbyApi, it triggers LOBBY_CREATED
- Handle of DEPLOYMENT_MATCHMAKER_RESOURCE_CREATED creates matchmaker using MatchmakerApi, it triggers
  MATCHMAKER_CREATED

- Handler of LOBBY_CREATED creates Runtime using RuntimeApi, it triggers RUNTIME_CREATED
- Handler of MATCHMAKER_CREATED creates OPEN_MATCHMAKER command for DeploymentTask
- Handler of RUNTIME_CREATED creates OPEN_LOBBY command for DeploymentTask

- DeploymentTask handles OPEN_LOBBY command, it changes DeploymentLobbyResource status from PENDING to CREATED
- DeploymentTask handles OPEN_MATCHMAKER command, it changes DeploymentMatchmakerResource status from PENDING to CREATED

- Next iteration of DeploymentTask handles DeploymentRequest entities
    - It creates DeploymentLobbyAssignment, it triggers DEPLOYMENT_LOBBY_ASSIGNMENT_CREATED
    - It creates DeploymentMatchmakerAssignment, it triggers DEPLOYMENT_MATCHMAKER_ASSIGNMENT_CREATED

- Handler of DEPLOYMENT_LOBBY_ASSIGNMENT_CREATED creates ASSIGN_CLIENT command for RuntimeTask
- Handler of DEPLOYMENT_MATCHMAKER_ASSIGNMENT_CREATED does nothing

# Matchmaking requested

- Customer runtime implementation creates REQUEST_MATCHMAKING message
- RuntimeApi handles REQUEST_MATCHMAKING message, it creates MatchmakerRequest for MatchmakerTask
- MatchmakerTask handles MatchmakerRequest entities
    - It creates MatchmakerMatchResource, it triggers MATCHMAKER_MATCH_RESOURCE_CREATED
- Handler of MATCHMAKER_MATCH_RESOURCE_CREATED creates Match using MatchApi, it triggers MATCH_CREATED
- Handler of MATCH_CREATED create Runtime using RuntimeApi, it triggers RUNTIME_CREATED
- Handler of RUNTIME_CREATED creates OPEN_MATCH command for MatchmakerTask

- MatchmakerTask handles OPEN_MATCH command, it changes MatchmakerMatchResource status from PENDING to CREATED

- Next iteration of MatchmakerTask handles MatchmakerRequest entities
    - It creates MatchmakerMatchAssignment, it triggers DEPLOYMENT_MATCHMAKER_ASSIGNMENT_CREATED

- Handler of DEPLOYMENT_MATCHMAKER_ASSIGNMENT_CREATED
    - It creates KICK_CLIENT command for DeploymentTask
    - It creates ASSIGN_CLIENT command for RuntimeTask

- DeploymentTask handles KICK_CLIENT command
    - It deletes DeploymentLobbyAssignment, it triggers DEPLOYMENT_LOBBY_ASSIGNMENT_DELETED

# Client became inactive

- RuntimeTask detects inactive clients and delete all of them using ClientApi, it triggers CLIENT_DELETED

- Handler of CLIENT_DELETED creates REMOVE_CLIENT command for DeploymentTask
- DeploymentTask handles REMOVE_CLIENT command
    - It removes all client requests, if any
    - It removes all client lobby assignments, if any, it triggers DEPLOYMENT_LOBBY_ASSIGNMENT_DELETED
    - It removes all client matchmaker assignments, if any, it triggers DEPLOYMENT_MATCHMAKER_ASSIGNMENT_DELETED

- Handler of DEPLOYMENT_MATCHMAKER_ASSIGNMENT_DELETED creates REMOVE_CLIENT command for MatchmakerTask
- MatchmakerTask handles REMOVE_CLIENT command
    - It deletes all client MatchmakerRequests, if any
    - It deletes all client MatchmakerMatchAssignments, if any, it triggers MATCHMAKER_MATCH_ASSIGNMENT_DELETED

- Handler of DEPLOYMENT_LOBBY_ASSIGNMENT_DELETED creates REMOVE_CLIENT command for RuntimeTask
- Handler of MATCHMAKER_MATCH_ASSIGNMENT_DELETED creates REMOVE_CLIENT command for RuntimeTask

- RuntimeTask handles REMOVE_CLIENT command
    - It removes client RuntimeAssignment entities, if any, it triggers RUNTIME_ASSIGNMENT_DELETED
- Handler of RUNTIME_ASSIGNMENT_DELETED creates CLIENT_REMOVED message to Runtime

# Runtime became inactive

- RuntimeTask detects that runtime became inactive
    - If it is LOBBY runtime, it creates DELETE_LOBBY deployment command
    - If it is MATCH runtime, it creates DELETE_MATCH matchmaker command

- DeploymentTask handles DELETE_LOBBY command
    - It deletes DeploymentLobbyResource, it triggers DEPLOYMENT_LOBBY_RESOURCE_DELETED
- Handler of DEPLOYMENT_LOBBY_RESOURCE_DELETED deletes Lobby, it triggers LOBBY_DELETED
- Handler of LOBBY_DELETED deletes Runtime using RuntimeApi, it triggers RUNTIME_DELETED

- MatchmakerTask handles DELETE_MATCH command
    - It deletes MatchmakerMatchResource, it triggers MATCHMAKER_MATCH_RESOURCE_DELETED
- Handler of MATCHMAKER_MATCH_RESOURCE_DELETED deletes Match, it triggers MATCH_DELETED
- Handler of MATCH_DELETED deletes Runtime using RuntimeApi, it triggers RUNTIME_DELETED

- Handler of RUNTIME_DELETED
    - It deletes all RuntimeCommands, if any
    - It deletes all RuntimeMessages, if any
    - It creates DELETE_CONTAINER command for PoolTask
    - It deletes Runtime job definition