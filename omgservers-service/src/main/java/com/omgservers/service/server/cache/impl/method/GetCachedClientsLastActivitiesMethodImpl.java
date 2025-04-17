package com.omgservers.service.server.cache.impl.method;

import com.omgservers.service.server.cache.dto.GetCachedClientsLastActivitiesRequest;
import com.omgservers.service.server.cache.dto.GetCachedClientsLastActivitiesResponse;
import com.omgservers.service.server.cache.impl.operation.ExecuteCacheCommandOperation;
import com.omgservers.service.server.cache.impl.operation.GetClientLastActivityCacheKeyOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetCachedClientsLastActivitiesMethodImpl implements GetCachedClientsLastActivitiesMethod {

    final GetClientLastActivityCacheKeyOperation getClientLastActivityCacheKeyOperation;
    final ExecuteCacheCommandOperation executeCacheCommandOperation;

    @Override
    public Uni<GetCachedClientsLastActivitiesResponse> execute(final GetCachedClientsLastActivitiesRequest request) {
        final var clientIdByCacheKey = request.getClientIds().stream().distinct().collect(Collectors.toMap(
                getClientLastActivityCacheKeyOperation::execute, Function.identity()));
        final var cacheKeys = clientIdByCacheKey.keySet().stream().toList();

        return executeCacheCommandOperation.getInstantKeys(cacheKeys)
                .map(result -> result.entrySet().stream().collect(Collectors.toMap(
                        e -> clientIdByCacheKey.get(e.getKey()),
                        Map.Entry::getValue)))
                .map(GetCachedClientsLastActivitiesResponse::new);
    }
}
