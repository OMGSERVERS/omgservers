# Player messages

## Incoming messages

```
{
  "id": "<id>",
  "qualifier": "SERVER_WELCOME_MESSAGE",
  "body": {
    "tenant_id": "<tenant_id>",
    "version_id": "<version_id>",
    "version_created": "<timestamp>"
  }
}
```

```
{
  "id": "<id>",
  "qualifier": "MATCHMAKER_ASSIGNMENT_MESSAGE",
  "body": {
    "matchmaker_id": "<matchmaker_id>"
  }
}
```

```
{
  "id": "<id>",
  "qualifier": "RUNTIME_ASSIGNMENT_MESSAGE",
  "body": {
    "runtime_id": "<runtime_id>",
    "runtime_qualifier": "<runtime_qualifier>",
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
  "qualifier": "CONNECTION_UPGRADE_MESSAGE",
  "body": {
    "client_id": "<client_id>",
    "protocol": "<protocol>",
    "web_socket_config": {
      "ws_token": "<ws_token>"
    }
  }
}
```

```
{
  "id": "<id>",
  "qualifier": "DISCONNECTION_REASON_MESSAGE",
  "body": {
    "reason": "<reason>"
  }
}
```

```
{
  "id": "<id>",
  "qualifier": "SERVER_OUTGOING_MESSAGE",
  "body": {
    "message": <message>
  }
}
```

## Outgoing messages

```
{
  "id": "<id>",
  "qualifier": "CLIENT_OUTGOING_MESSAGE",
  "body": {
    "data": <data>
  }
}
```