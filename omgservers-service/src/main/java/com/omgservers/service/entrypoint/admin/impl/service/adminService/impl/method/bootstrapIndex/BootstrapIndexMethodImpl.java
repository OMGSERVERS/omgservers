package com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method.bootstrapIndex;

import com.omgservers.model.dto.server.BootstrapIndexServerRequest;
import com.omgservers.model.dto.server.BootstrapIndexServerResponse;
import com.omgservers.model.dto.system.FindIndexRequest;
import com.omgservers.model.dto.system.FindIndexResponse;
import com.omgservers.model.dto.system.SyncIndexRequest;
import com.omgservers.model.dto.system.SyncIndexResponse;
import com.omgservers.model.index.IndexConfigModel;
import com.omgservers.model.index.IndexModel;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.system.IndexModelFactory;
import com.omgservers.service.module.pool.PoolModule;
import com.omgservers.service.module.root.RootModule;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class BootstrapIndexMethodImpl implements BootstrapIndexMethod {

    final RootModule rootModule;
    final PoolModule poolModule;
    final SystemModule systemModule;

    final GetConfigOperation getConfigOperation;

    final IndexModelFactory indexModelFactory;

    @Override
    public Uni<BootstrapIndexServerResponse> bootstrapIndex(final BootstrapIndexServerRequest request) {
        log.debug("Bootstrap index, request={}", request);

        final var indexName = getConfigOperation.getServiceConfig().index().name();
        return findIndex(indexName)
                .replaceWith(Boolean.FALSE)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithUni(t -> {
                    final var shardCount = request.getShardCount();
                    final var servers = request.getServers();

                    final var indexConfig = IndexConfigModel.create(servers, shardCount);
                    final var index = indexModelFactory.create(indexName, indexConfig);
                    return syncIndex(index);
                })
                .map(BootstrapIndexServerResponse::new);
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
