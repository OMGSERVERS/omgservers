package com.omgservers.service.service.cache.impl.service.inmemory.method;

import com.omgservers.service.service.cache.dto.GetClientsLastActivitiesRequest;
import com.omgservers.service.service.cache.dto.GetClientsLastActivitiesResponse;
import com.omgservers.service.service.cache.impl.operation.GetClientLastActivityCacheKeyOperation;
import com.omgservers.service.service.cache.impl.service.inmemory.component.InMemoryCache;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.HashMap;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetClientsLastActivitiesMethodImpl implements GetClientsLastActivitiesMethod {

    final GetClientLastActivityCacheKeyOperation getClientLastActivityCacheKeyOperation;
    final InMemoryCache inMemoryCache;

    @Override
    public Uni<GetClientsLastActivitiesResponse> execute(final GetClientsLastActivitiesRequest request) {
        final var lastActivities = new HashMap<Long, Instant>();
        request.getClientIds()
                .forEach(clientId -> {
                    final var cacheKey = getClientLastActivityCacheKeyOperation.execute(clientId);
                    final var lastActivity = inMemoryCache.getInstant(cacheKey);
                    lastActivities.put(clientId, lastActivity);
                });

        return Uni.createFrom()
                .item(new GetClientsLastActivitiesResponse(lastActivities));
    }
}
