package com.omgservers.service.factory.pool;

import com.omgservers.schema.model.poolRequest.PoolRequestConfigModel;
import com.omgservers.schema.model.poolRequest.PoolRequestModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class PoolRequestModelFactory {

    final GenerateIdOperation generateIdOperation;

    public PoolRequestModel create(final Long poolId,
                                   final Long runtimeId,
                                   final PoolRequestConfigModel config) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, poolId, runtimeId, config, idempotencyKey);
    }

    public PoolRequestModel create(final Long poolId,
                                   final Long runtimeId,
                                   final PoolRequestConfigModel config,
                                   final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, poolId, runtimeId, config, idempotencyKey);
    }

    public PoolRequestModel create(final Long id,
                                   final Long poolId,
                                   final Long runtimeId,
                                   final PoolRequestConfigModel config,
                                   final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var poolRequest = new PoolRequestModel();
        poolRequest.setId(id);
        poolRequest.setIdempotencyKey(idempotencyKey);
        poolRequest.setPoolId(poolId);
        poolRequest.setCreated(now);
        poolRequest.setModified(now);
        poolRequest.setRuntimeId(runtimeId);
        poolRequest.setConfig(config);
        poolRequest.setDeleted(Boolean.FALSE);

        return poolRequest;
    }
}
