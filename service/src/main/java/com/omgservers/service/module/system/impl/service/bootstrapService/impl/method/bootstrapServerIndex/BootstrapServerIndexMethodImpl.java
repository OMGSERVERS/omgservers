package com.omgservers.service.module.system.impl.service.bootstrapService.impl.method.bootstrapServerIndex;

import com.omgservers.model.dto.system.SyncIndexRequest;
import com.omgservers.model.index.IndexConfigModel;
import com.omgservers.service.factory.system.IndexModelFactory;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class BootstrapServerIndexMethodImpl implements BootstrapServerIndexMethod {

    final SystemModule systemModule;

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
        return systemModule.getIndexService().syncIndexWithIdempotency(request)
                .replaceWithVoid();
    }
}
