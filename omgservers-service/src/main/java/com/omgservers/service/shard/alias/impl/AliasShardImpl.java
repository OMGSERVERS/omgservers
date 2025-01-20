package com.omgservers.service.shard.alias.impl;

import com.omgservers.service.shard.alias.AliasShard;
import com.omgservers.service.shard.alias.impl.service.aliasService.AliasService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AliasShardImpl implements AliasShard {

    final AliasService aliasService;

    @Override
    public AliasService getService() {
        return aliasService;
    }
}
