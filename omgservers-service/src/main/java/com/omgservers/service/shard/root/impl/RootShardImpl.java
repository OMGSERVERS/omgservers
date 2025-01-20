package com.omgservers.service.shard.root.impl;

import com.omgservers.service.shard.root.RootShard;
import com.omgservers.service.shard.root.impl.service.rootService.RootService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class RootShardImpl implements RootShard {

    final RootService rootService;

    @Override
    public RootService getService() {
        return rootService;
    }
}
