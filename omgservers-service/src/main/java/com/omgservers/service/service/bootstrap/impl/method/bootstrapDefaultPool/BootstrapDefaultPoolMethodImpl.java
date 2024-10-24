package com.omgservers.service.service.bootstrap.impl.method.bootstrapDefaultPool;

import com.omgservers.schema.model.pool.PoolModel;
import com.omgservers.schema.module.pool.pool.SyncPoolRequest;
import com.omgservers.service.factory.pool.PoolModelFactory;
import com.omgservers.service.factory.pool.PoolServerModelFactory;
import com.omgservers.service.module.pool.PoolModule;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import com.omgservers.service.operation.getServers.GetServersOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class BootstrapDefaultPoolMethodImpl implements BootstrapDefaultPoolMethod {

    final PoolModule poolModule;

    final GetServersOperation getServersOperation;
    final GetConfigOperation getConfigOperation;

    final PoolServerModelFactory poolServerModelFactory;
    final PoolModelFactory poolModelFactory;

    @Override
    public Uni<Void> execute() {
        log.debug("Bootstrap default pool");

        return createDefaultPool()
                .invoke(defaultPool -> log.info("Default pool was created, {}", defaultPool))
                .replaceWithVoid();
    }

    Uni<PoolModel> createDefaultPool() {
        final var defaultPoolId = getConfigOperation.getServiceConfig().defaults().poolId();
        final var pool = poolModelFactory.create(defaultPoolId, "bootstrap/defaultPool");
        final var request = new SyncPoolRequest(pool);
        return poolModule.getPoolService().executeWithIdempotency(request)
                .replaceWith(pool);
    }
}
