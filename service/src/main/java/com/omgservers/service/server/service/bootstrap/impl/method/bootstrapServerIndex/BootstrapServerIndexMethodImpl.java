package com.omgservers.service.server.service.bootstrap.impl.method.bootstrapServerIndex;

import com.omgservers.schema.model.index.IndexConfigModel;
import com.omgservers.schema.service.system.SyncIndexRequest;
import com.omgservers.service.factory.system.IndexModelFactory;
import com.omgservers.service.server.operation.getConfig.GetConfigOperation;
import com.omgservers.service.server.service.index.IndexService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class BootstrapServerIndexMethodImpl implements BootstrapServerIndexMethod {

    final IndexService indexService;

    final GetConfigOperation getConfigOperation;

    final IndexModelFactory indexModelFactory;

    @Override
    public Uni<Void> bootstrapServerIndex() {
        log.debug("Bootstrap server index");

        final var indexId = getConfigOperation.getServiceConfig().defaults().indexId();
        final var servers = getConfigOperation.getServiceConfig().bootstrap().index().servers();
        final var shardCount = getConfigOperation.getServiceConfig().index().shardCount();
        final var indexConfig = IndexConfigModel.create(servers, shardCount);
        final var index = indexModelFactory.create(indexId, indexConfig, "bootstrap/index");

        final var request = new SyncIndexRequest(index);
        return indexService.syncIndexWithIdempotency(request)
                .replaceWithVoid();
    }
}
