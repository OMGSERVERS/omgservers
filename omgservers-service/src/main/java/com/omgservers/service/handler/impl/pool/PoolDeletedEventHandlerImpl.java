package com.omgservers.service.handler.impl.pool;

import com.omgservers.schema.model.poolState.PoolStateDto;
import com.omgservers.schema.shard.pool.poolState.GetPoolStateRequest;
import com.omgservers.schema.shard.pool.poolState.GetPoolStateResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.pool.PoolDeletedEventBodyModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.operation.task.DeleteTaskOperation;
import com.omgservers.service.shard.pool.PoolShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PoolDeletedEventHandlerImpl implements EventHandler {

    final PoolShard poolShard;

    final DeleteTaskOperation deleteTaskOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.POOL_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (PoolDeletedEventBodyModel) event.getBody();
        final var poolId = body.getId();

        return getPoolState(poolId)
                .flatMap(poolState -> {
                    final var pool = poolState.getPool();
                    log.debug("Deleted, {}", pool);

                    final var poolCommands = poolState.getPoolCommands();
                    final var poolRequests = poolState.getPoolRequests();
                    final var poolContainers = poolState.getPoolContainers();

                    if (!poolCommands.isEmpty() ||
                            !poolRequests.isEmpty() ||
                            !poolContainers.isEmpty()) {
                        log.warn("Pool \"{}\" deleted, but some data remains, " +
                                        "commands={}, " +
                                        "requests={}, " +
                                        "containers={}",
                                poolId,
                                poolCommands.size(),
                                poolRequests.size(),
                                poolContainers.size());
                    }

                    return deleteTaskOperation.execute(poolId);
                })
                .replaceWithVoid();
    }

    Uni<PoolStateDto> getPoolState(final Long poolId) {
        final var request = new GetPoolStateRequest(poolId);
        return poolShard.getService().execute(request)
                .map(GetPoolStateResponse::getPoolState);
    }
}
