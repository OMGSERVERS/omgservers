package com.omgservers.service.server.task.impl.method.executeRuntimeTask.operation;

import com.omgservers.service.server.cache.CacheService;
import com.omgservers.service.server.cache.dto.CacheRuntimeLastActivityRequest;
import com.omgservers.service.server.cache.dto.GetCachedRuntimeLastActivityRequest;
import com.omgservers.service.server.cache.dto.GetCachedRuntimeLastActivityResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class GetRuntimeLastActivityOperationImpl implements GetRuntimeLastActivityOperation {

    final CacheService cacheService;

    @Override
    public Uni<Instant> execute(final Long runtimeId) {
        return getRuntimeLastActivity(runtimeId)
                .onItem().ifNull().switchTo(() -> {
                    log.info("No last activity for runtime \"{}\", set current timestamp", runtimeId);

                    final var lastActivity = Instant.now();
                    return cacheRuntimeLastActivity(runtimeId, lastActivity);
                });
    }

    Uni<Instant> getRuntimeLastActivity(final Long runtimeId) {
        final var request = new GetCachedRuntimeLastActivityRequest(runtimeId);
        return cacheService.execute(request)
                .map(GetCachedRuntimeLastActivityResponse::getLastActivity);
    }

    Uni<Instant> cacheRuntimeLastActivity(final Long runtimeId, final Instant lastActivity) {
        final var request = new CacheRuntimeLastActivityRequest(runtimeId, lastActivity);
        return cacheService.execute(request)
                .replaceWith(lastActivity);
    }
}
