package com.omgservers.service.shard.runtime.impl;

import com.omgservers.service.shard.runtime.RuntimeShard;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.RuntimeService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class RuntimeShardImpl implements RuntimeShard {

    final RuntimeService runtimeService;

    public RuntimeService getService() {
        return runtimeService;
    }

}
