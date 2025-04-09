package com.omgservers.service.server.cache.impl.operation;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetRuntimeLastActivityCacheKeyOperationImpl implements GetRuntimeLastActivityCacheKeyOperation {

    @Override
    public String execute(final Long runtimeId) {
        return String.format("service:runtime:%d:last-activity", runtimeId);
    }
}
