package com.omgservers.service.server.cache.impl.operation;

public interface GetClientLastActivityCacheKeyOperation {
    String execute(Long clientId);
}
