# OMGSERVERS

[![Build](https://github.com/OMGSERVERS/omgservers/actions/workflows/build.yml/badge.svg)](https://github.com/OMGSERVERS/omgservers/actions/workflows/build.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=OMGSERVERS_omgservers&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=OMGSERVERS_omgservers)

### Environment variables:

- OMGSERVERS_ROOT_LOG_LEVEL
- OMGSERVERS_APP_LOG_LEVEL
- OMGSERVERS_TRAFFIC_LOG_LEVEL
- OMGSERVERS_CONSOLE_LOG_ENABLED
- OMGSERVERS_ACCESS_LOG_ENABLED
- OMGSERVERS_DATASOURCE_URL
- OMGSERVERS_DATASOURCE_USERNAME
- OMGSERVERS_DATASOURCE_PASSWORD
- OMGSERVERS_SCHEDULER_ENABLED
- OMGSERVERS_AMQP_HOST
- OMGSERVERS_AMQP_PORT
- OMGSERVERS_AMQP_USERNAME
- OMGSERVERS_AMQP_PASSWORD
- OMGSERVERS_INDEX_NAME
- OMGSERVERS_MIGRATION_CONCURRENCY
- OMGSERVERS_DISABLE_MIGRATION
- OMGSERVERS_DISABLE_RELAY
- OMGSERVERS_DATACENTER_ID
- OMGSERVERS_NODE_ID
- OMGSERVERS_EXTERNAL_URI
- OMGSERVERS_INTERNAL_URI
- OMGSERVERS_SERVICE_USERNAME
- OMGSERVERS_SERVICE_PASSWORD
- OMGSERVERS_ADMIN_USERNAME
- OMGSERVERS_ADMIN_PASSWORD
- OMGSERVERS_ADDRESSES
- OMGSERVERS_SHARD_COUNT
- OMGSERVERS_BOOTSTRAP_SERVICE
- OMGSERVERS_TOKEN_LIFETIME
- OMGSERVERS_WORKERS_NETWORK
- OMGSERVERS_DISABLE_DOCKER
- OMGSERVERS_DOCKER_HOST

### Incoming commands

```
{
    qualifier = "add_client"
    user_id = <user_id>,
    client_id = <client_id>,
    attributes = {},
    profile = {}
}
```

```
{
    qualifier = "delete_client"
    user_id = <user_id>,
    client_id = <client_id>    
}
```

```
{
    qualifier = "change_player"
    user_id = <user_id>,
    client_id = <client_id>,
    attributes = {},
    profile = {},
    message = {}
}
```

```
{
    qualifier = "handle_message"
    user_id = <user_id>,
    client_id = <client_id>,    
    message = {}
}
```

```
{
    qualifier = "init_runtime"
    config = {}
}
```

```
{
    qualifier = "sign_in"
    user_id = <user_id>,
    client_id = <client_id>,
    attributes = {},
    profile = {}    
}
```

```
{
    qualifier = "sign_up"
    user_id = <user_id>,
    client_id = <client_id>    
}
```

```
{
    qualifier = "update_runtime"
    time = <time>    
}
```

### Outgoing commands

```
{
    qualifier = "respond",
    user_id = <user_id>,
    client_id = <client_id>,
    message = {}
}
```

```
{
    qualifier = "set_attributes",
    user_id = <user_id>,
    client_id = <client_id>,
    attributes = {}
}
```

```
{
    qualifier = "set_profile",
    user_id = <user_id>,
    client_id = <client_id>,
    profile = {}
}
```

```
{
    qualifier = "unicast",
    user_id = <user_id>,
    client_id = <client_id>,
    message = {}
}
```

```
{
    qualifier = "multicast",
    recipients = {
        {
            user_id = <user_id_1>,
            client_id = <client_id_1>
        },
        ...
        {
            user_id = <user_id_N>,
            client_id = <client_id_N>
        }
    }
    message = {}
}
```

```
{
    qualifier = "broadcast",    
    message = {}
}
```

```
{
    qualifier = "change",    
    user_id = <user_id>,
    client_id = <client_id>,
    message = {}
}
```

```
{
    qualifier = "kick",    
    user_id = <user_id>,
    client_id = <client_id>    
}
```

```
{
    qualifier = "stop",    
    reason = "<reason>"    
}
```