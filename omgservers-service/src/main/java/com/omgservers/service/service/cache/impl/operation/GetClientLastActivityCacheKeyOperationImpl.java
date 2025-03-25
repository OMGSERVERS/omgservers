package com.omgservers.service.service.cache.impl.operation;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetClientLastActivityCacheKeyOperationImpl implements GetClientLastActivityCacheKeyOperation {

    @Override
    public String execute(final Long clientId) {
        return String.format("service:client:%d:last-activity", clientId);
    }
}
