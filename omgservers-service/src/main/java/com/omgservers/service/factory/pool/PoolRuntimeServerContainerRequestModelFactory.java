package com.omgservers.service.factory.pool;

import com.omgservers.model.poolRuntimeServerContainerRequest.PoolRuntimeServerContainerRequestConfigModel;
import com.omgservers.model.poolRuntimeServerContainerRequest.PoolRuntimeServerContainerRequestModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class PoolRuntimeServerContainerRequestModelFactory {

    final GenerateIdOperation generateIdOperation;

    public PoolRuntimeServerContainerRequestModel create(final Long poolId,
                                                         final Long runtimeId,
                                                         final PoolRuntimeServerContainerRequestConfigModel config) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, poolId, runtimeId, config, idempotencyKey);
    }

    public PoolRuntimeServerContainerRequestModel create(final Long poolId,
                                                         final Long runtimeId,
                                                         final PoolRuntimeServerContainerRequestConfigModel config,
                                                         final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, poolId, runtimeId, config, idempotencyKey);
    }

    public PoolRuntimeServerContainerRequestModel create(final Long id,
                                                         final Long poolId,
                                                         final Long runtimeId,
                                                         final PoolRuntimeServerContainerRequestConfigModel config,
                                                         final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var poolRuntimeServerContainerRequest = new PoolRuntimeServerContainerRequestModel();
        poolRuntimeServerContainerRequest.setId(id);
        poolRuntimeServerContainerRequest.setIdempotencyKey(idempotencyKey);
        poolRuntimeServerContainerRequest.setPoolId(poolId);
        poolRuntimeServerContainerRequest.setCreated(now);
        poolRuntimeServerContainerRequest.setModified(now);
        poolRuntimeServerContainerRequest.setRuntimeId(runtimeId);
        poolRuntimeServerContainerRequest.setConfig(config);
        poolRuntimeServerContainerRequest.setDeleted(false);

        return poolRuntimeServerContainerRequest;
    }
}
