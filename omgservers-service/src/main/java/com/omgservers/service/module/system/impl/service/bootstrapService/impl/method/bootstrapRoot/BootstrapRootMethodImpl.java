package com.omgservers.service.module.system.impl.service.bootstrapService.impl.method.bootstrapRoot;

import com.omgservers.model.dto.root.SyncRootRequest;
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

        final var rootId = getConfigOperation.getServiceConfig().defaults().rootId();

        final var root = rootModelFactory.create(rootId, "bootstrap");

        final var request = new SyncRootRequest(root);
        return rootModule.getRootService().syncRootWithIdempotency(request)
                .replaceWithVoid();
    }
}
