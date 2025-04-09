package com.omgservers.service.server.task.impl.method.executeRuntimeTask.operation;

import com.omgservers.service.server.cache.CacheService;
import com.omgservers.service.server.cache.dto.GetRuntimeLastActivityRequest;
import com.omgservers.service.server.cache.dto.GetRuntimeLastActivityResponse;
import com.omgservers.service.server.cache.dto.SetRuntimeLastActivityRequest;
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
                    return setRuntimeLastActivity(runtimeId, lastActivity);
                });
    }

    Uni<Instant> getRuntimeLastActivity(final Long runtimeId) {
        final var request = new GetRuntimeLastActivityRequest(runtimeId);
        return cacheService.execute(request)
                .map(GetRuntimeLastActivityResponse::getLastActivity);
    }

    Uni<Instant> setRuntimeLastActivity(final Long runtimeId, final Instant lastActivity) {
        final var request = new SetRuntimeLastActivityRequest(runtimeId, lastActivity);
        return cacheService.execute(request)
                .replaceWith(lastActivity);
    }
}
