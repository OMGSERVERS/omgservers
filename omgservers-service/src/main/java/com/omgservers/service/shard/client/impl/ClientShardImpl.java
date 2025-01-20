package com.omgservers.service.shard.client.impl;

import com.omgservers.service.shard.client.ClientShard;
import com.omgservers.service.shard.client.impl.service.clientService.ClientService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ClientShardImpl implements ClientShard {

    final ClientService clientService;

    @Override
    public ClientService getService() {
        return clientService;
    }
}
