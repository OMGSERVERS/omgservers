package com.omgservers.service.module.system.impl.service.bootstrapService.impl.method.bootstrapServerIndex;

import com.omgservers.model.dto.system.FindIndexRequest;
import com.omgservers.model.dto.system.FindIndexResponse;
import com.omgservers.model.dto.system.SyncIndexRequest;
import com.omgservers.model.dto.system.SyncIndexResponse;
import com.omgservers.model.index.IndexConfigModel;
import com.omgservers.model.index.IndexModel;
import com.omgservers.service.exception.ServerSideNotFoundException;
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

        final var indexName = getConfigOperation.getServiceConfig().index().name();
        return findIndex(indexName)
                .invoke(root -> log.info("Server index was already create, skip operation, indexName={}", indexName))
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithUni(t -> {
                    final var servers = getConfigOperation.getServiceConfig().bootstrap().index().servers();
                    final var shardCount = getConfigOperation.getServiceConfig().index().shardCount();
                    final var indexConfig = IndexConfigModel.create(servers, shardCount);
                    final var indexModel = indexModelFactory.create(indexName, indexConfig);

                    return syncIndex(indexModel)
                            .replaceWith(indexModel);
                })
                .replaceWithVoid();
    }

    Uni<IndexModel> findIndex(final String indexName) {
        final var request = new FindIndexRequest(indexName);
        return systemModule.getIndexService().findIndex(request)
                .map(FindIndexResponse::getIndex);
    }

    Uni<Boolean> syncIndex(final IndexModel index) {
        final var request = new SyncIndexRequest(index);
        return systemModule.getIndexService().syncIndex(request)
                .map(SyncIndexResponse::getCreated);
    }
}
