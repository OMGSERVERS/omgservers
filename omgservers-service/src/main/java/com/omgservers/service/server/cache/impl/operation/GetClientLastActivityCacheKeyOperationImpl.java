package com.omgservers.service.server.cache.impl.operation;

import com.omgservers.service.configuration.CacheKeyQualifierEnum;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetClientLastActivityCacheKeyOperationImpl implements GetClientLastActivityCacheKeyOperation {

    @Override
    public String execute(final Long clientId) {
        return String.format(CacheKeyQualifierEnum.CLIENT_LAST_ACTIVITY.getKey(),
                clientId);
    }
}
