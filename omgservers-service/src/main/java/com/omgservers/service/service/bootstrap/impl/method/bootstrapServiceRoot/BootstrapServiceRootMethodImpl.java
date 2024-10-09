package com.omgservers.service.service.bootstrap.impl.method.bootstrapServiceRoot;

import com.omgservers.schema.module.root.root.SyncRootRequest;
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
class BootstrapServiceRootMethodImpl implements BootstrapServiceRootMethod {

    final RootModule rootModule;

    final GetConfigOperation getConfigOperation;

    final RootModelFactory rootModelFactory;

    @Override
    public Uni<Void> bootstrapServiceRoot() {
        log.debug("Bootstrap service root");

        final var rootId = getConfigOperation.getServiceConfig().defaults().rootId();

        final var root = rootModelFactory.create(rootId, "bootstrap/root");

        final var request = new SyncRootRequest(root);
        return rootModule.getService().syncRootWithIdempotency(request)
                .replaceWithVoid();
    }
}
