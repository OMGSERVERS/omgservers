package com.omgservers.service.shard.client;

import com.omgservers.service.shard.client.impl.service.clientService.ClientService;

public interface ClientShard {
    ClientService getService();
}
