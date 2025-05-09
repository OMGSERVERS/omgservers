package com.omgservers.service.server.task.impl.method.executeRuntimeTask.operation;

import com.omgservers.service.server.cache.CacheService;
import com.omgservers.service.server.cache.dto.GetCachedClientsLastActivitiesRequest;
import com.omgservers.service.server.cache.dto.GetCachedClientsLastActivitiesResponse;
import com.omgservers.service.server.cache.dto.CacheClientLastActivityRequest;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class GetClientsLastActivitiesOperationImpl implements GetClientsLastActivitiesOperation {

    final CacheService cacheService;

    @Override
    public Uni<Map<Long, Instant>> execute(List<Long> clientIds) {
        return getClientsLastActivities(clientIds)
                .flatMap(lastActivities -> {
                    final var now = Instant.now();

                    return Multi.createFrom().iterable(clientIds)
                            .onItem().transformToUniAndConcatenate(clientId -> {
                                final var lastActivity = lastActivities.get(clientId);
                                if (Objects.nonNull(lastActivity)) {
                                    return Uni.createFrom().voidItem();
                                } else {
                                    log.info("No last activity for client \"{}\", set current timestamp", clientId);

                                    return cacheClientLastActivity(clientId, now)
                                            .invoke(voidItem -> lastActivities.put(clientId, now));
                                }
                            })
                            .collect().asList()
                            .replaceWith(lastActivities);
                });
    }

    Uni<Map<Long, Instant>> getClientsLastActivities(final List<Long> clientIds) {
        final var request = new GetCachedClientsLastActivitiesRequest(clientIds);
        return cacheService.execute(request)
                .map(GetCachedClientsLastActivitiesResponse::getLastActivities);
    }

    Uni<Void> cacheClientLastActivity(final Long clientId, final Instant lastActivity) {
        final var request = new CacheClientLastActivityRequest(clientId, lastActivity);
        return cacheService.execute(request)
                .replaceWithVoid();
    }
}
