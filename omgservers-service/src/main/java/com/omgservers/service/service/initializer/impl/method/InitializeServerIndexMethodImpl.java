package com.omgservers.service.service.initializer.impl.method;

import com.omgservers.schema.model.index.IndexConfigDto;
import com.omgservers.service.factory.system.IndexModelFactory;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import com.omgservers.service.service.index.IndexService;
import com.omgservers.service.service.index.dto.SyncIndexRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class InitializeServerIndexMethodImpl implements InitializeServerIndexMethod {

    final IndexService indexService;

    final GetConfigOperation getConfigOperation;

    final IndexModelFactory indexModelFactory;

    @Override
    public Uni<Void> execute() {
        log.debug("Initialize server index");

        final var servers = getConfigOperation.getServiceConfig().initialization().serverIndex().servers();
        final var shardCount = getConfigOperation.getServiceConfig().index().shardCount();
        final var indexConfig = IndexConfigDto.create(servers, shardCount);
        final var index = indexModelFactory.create(indexConfig, "index");

        final var request = new SyncIndexRequest(index);
        return indexService.syncIndexWithIdempotency(request)
                .replaceWithVoid();
    }
}
