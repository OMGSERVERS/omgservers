package com.omgservers.service.module.system.impl.service.bootstrapService.impl.method.bootstrapRoot;

import com.omgservers.model.dto.root.GetRootRequest;
import com.omgservers.model.dto.root.GetRootResponse;
import com.omgservers.model.dto.root.SyncRootRequest;
import com.omgservers.model.dto.root.SyncRootResponse;
import com.omgservers.model.root.RootModel;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.root.RootModelFactory;
import com.omgservers.service.module.root.RootModule;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class BootstrapRootMethodImpl implements BootstrapRootMethod {

    final RootModule rootModule;

    final GetConfigOperation getConfigOperation;

    final RootModelFactory rootModelFactory;

    @Override
    public Uni<Void> bootstrapRoot() {
        log.debug("Bootstrap root");

        final var rootId = getConfigOperation.getServiceConfig().bootstrap().root().rootId();
        return getRoot(rootId)
                .invoke(root -> log.info("Root was already create, skip operation, rootId={}", rootId))
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithUni(t -> {
                    final var idempotencyKey = "bootstrap";
                    final var root = rootModelFactory.create(rootId, idempotencyKey);

                    return syncRoot(root)
                            .replaceWith(root);
                })
                .replaceWithVoid();
    }

    Uni<RootModel> getRoot(final Long id) {
        final var getRootRequest = new GetRootRequest(id);
        return rootModule.getRootService().getRoot(getRootRequest)
                .map(GetRootResponse::getRoot);
    }

    Uni<Boolean> syncRoot(final RootModel root) {
        final var syncRootRequest = new SyncRootRequest(root);
        return rootModule.getRootService().syncRootWithIdempotency(syncRootRequest)
                .map(SyncRootResponse::getCreated);
    }
}
