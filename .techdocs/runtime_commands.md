# Runtime Commands

## Incoming commands

```
{
  "id": "<id>",
  "qualifier": "INIT_RUNTIME",
  "body": {
    "runtime_config": {
      "lobby_config": {
        "lobby_id": "<lobby_id>"
      },
      "match_config": {
        "matchmaker_id": "<matchmaker_id>",
        "match_id": "<match_id>"
      },
      "version_config": <version_config>
    }
  }
}
```

```
{
  "id": "<id>",
  "qualifier": "ADD_CLIENT",
  "body": {
    "user_id": "<user_id>",
    "client_id": "<client_id>",
    "profile": <profile>
  }
}
```

```
{
  "id": "<id>",
  "qualifier": "ADD_MATCH_CLIENT",
  "body": {
    "user_id": "<user_id>",
    "client_id": "<client_id>",
    "group_name": "<group_name>",
    "profile": <profile>
  }
}
```

```
{
  "id": "<id>",
  "qualifier": "DELETE_CLIENT",
  "body": {
    "client_id": "<client_id>"
  }
}
```

```
{
  "id": "<id>",
  "qualifier": "HANDLE_MESSAGE",
  "body": {
    "client_id": "<client_id>",
    "message": <message>
  }
}
```

## Outgoing commands

```
{
  "qualifier": "RESPOND_CLIENT",
  "body": {
    "client_id": "<client_id>",
    "message": <message>
  }
}
```

```
{
  "qualifier": "SET_PROFILE",
  "body": {
    "client_id": "<client_id>",
    "profile": <profile>
  }
}
```

```
{
  "qualifier": "MULTICAST_MESSAGE",
  "body": {
    "client": [
      "<client_id_1>",
      "<client_id_2>",
      ...
    ],
    "message": <message>
  }
}
```

```
{
  "qualifier": "BROADCAST_MESSAGE",
  "body": {
    "message": <message>
  }
}
```

```
{
  "qualifier": "REQUEST_MATCHMAKING",
  "body": {
    "client_id": "<client_id>",
    "mode": "<mode>"
  }
}
```

```
{
  "qualifier": "KICK_CLIENT",
  "body": {
    "client_id": "<client_id>"
  }
}
```

```
{
  "qualifier": "STOP_MATCHMAKING",
  "body": {
  }
}
```

```
{
  "qualifier": "UPGRADE_CONNECTION",
  "body": {
    "client_id": "<client_id>",
    "protocol": "<protocol>"
  }
}
```