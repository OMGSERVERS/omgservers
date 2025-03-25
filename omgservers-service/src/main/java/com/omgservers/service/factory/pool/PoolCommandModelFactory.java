package com.omgservers.service.factory.pool;

import com.omgservers.schema.model.poolCommand.PoolCommandBodyDto;
import com.omgservers.schema.model.poolCommand.PoolCommandModel;
import com.omgservers.service.operation.server.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class PoolCommandModelFactory {

    final GenerateIdOperation generateIdOperation;

    public PoolCommandModel create(final Long poolId,
                                   final PoolCommandBodyDto body) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();

        return create(id, poolId, body, idempotencyKey);
    }

    public PoolCommandModel create(final Long id,
                                   final Long poolId,
                                   final PoolCommandBodyDto body) {
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, poolId, body, idempotencyKey);
    }

    public PoolCommandModel create(final Long poolId,
                                   final PoolCommandBodyDto body,
                                   final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, poolId, body, idempotencyKey);
    }

    public PoolCommandModel create(final Long id,
                                   final Long poolId,
                                   final PoolCommandBodyDto body,
                                   final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var poolCommand = new PoolCommandModel();
        poolCommand.setId(id);
        poolCommand.setIdempotencyKey(idempotencyKey);
        poolCommand.setPoolId(poolId);
        poolCommand.setCreated(now);
        poolCommand.setModified(now);
        poolCommand.setQualifier(body.getQualifier());
        poolCommand.setBody(body);
        poolCommand.setDeleted(Boolean.FALSE);
        return poolCommand;
    }
}
