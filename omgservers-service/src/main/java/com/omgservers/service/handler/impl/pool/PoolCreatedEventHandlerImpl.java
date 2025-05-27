package com.omgservers.service.handler.impl.pool;

import com.omgservers.schema.model.entity.EntityQualifierEnum;
import com.omgservers.schema.model.pool.PoolModel;
import com.omgservers.schema.model.task.TaskQualifierEnum;
import com.omgservers.schema.shard.pool.pool.GetPoolRequest;
import com.omgservers.schema.shard.pool.pool.GetPoolResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.pool.PoolCreatedEventBodyModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.operation.entity.CreateEntityOperation;
import com.omgservers.service.operation.task.CreateTaskOperation;
import com.omgservers.service.shard.pool.PoolShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PoolCreatedEventHandlerImpl implements EventHandler {

    final PoolShard poolShard;

    final CreateEntityOperation createEntityOperation;
    final CreateTaskOperation createTaskOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.POOL_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (PoolCreatedEventBodyModel) event.getBody();
        final var poolId = body.getId();

        final var idempotencyKey = event.getId().toString();

        return getPool(poolId)
                .flatMap(pool -> {
                    log.debug("Created, {}", pool);

                    return createEntityOperation.execute(EntityQualifierEnum.POOL, poolId, idempotencyKey)
                            .flatMap(result -> createTaskOperation.execute(TaskQualifierEnum.POOL,
                                    poolId,
                                    idempotencyKey));
                })
                .replaceWithVoid();
    }

    Uni<PoolModel> getPool(final Long id) {
        final var request = new GetPoolRequest(id);
        return poolShard.getService().execute(request)
                .map(GetPoolResponse::getPool);
    }
}
