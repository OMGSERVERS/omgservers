package com.omgservers.service.module.system.impl.service.bootstrapService.impl.method.bootstrapDefaultPool;

import com.omgservers.model.dto.pool.pool.SyncPoolRequest;
import com.omgservers.service.factory.pool.PoolModelFactory;
import com.omgservers.service.module.pool.PoolModule;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
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

        final var pool = poolModelFactory.create(defaultPoolId, "bootstrap");

        final var request = new SyncPoolRequest(pool);
        return poolModule.getPoolService().syncPoolWithIdempotency(request)
                .replaceWithVoid();
    }
}
