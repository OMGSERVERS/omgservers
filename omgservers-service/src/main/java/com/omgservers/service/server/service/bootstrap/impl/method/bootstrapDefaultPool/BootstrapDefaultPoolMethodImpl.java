package com.omgservers.service.server.service.bootstrap.impl.method.bootstrapDefaultPool;

import com.omgservers.schema.module.pool.pool.SyncPoolRequest;
import com.omgservers.service.factory.pool.PoolModelFactory;
import com.omgservers.service.module.pool.PoolModule;
import com.omgservers.service.server.operation.getConfig.GetConfigOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class BootstrapDefaultPoolMethodImpl implements BootstrapDefaultPoolMethod {

    final PoolModule poolModule;

    final GetConfigOperation getConfigOperation;

    final PoolModelFactory poolModelFactory;

    @Override
    public Uni<Void> bootstrapDefaultPool() {
        log.debug("Bootstrap default pool");

        final var defaultPoolId = getConfigOperation.getServiceConfig().defaults().poolId();

        final var pool = poolModelFactory.create(defaultPoolId, "bootstrap/defaultPool");

        final var request = new SyncPoolRequest(pool);
        return poolModule.getPoolService().syncPoolWithIdempotency(request)
                .replaceWithVoid();
    }
}
