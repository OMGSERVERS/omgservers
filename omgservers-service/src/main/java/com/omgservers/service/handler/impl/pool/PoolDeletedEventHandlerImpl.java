package com.omgservers.service.handler.impl.pool;

import com.omgservers.schema.model.pool.PoolModel;
import com.omgservers.schema.model.poolServer.PoolServerModel;
import com.omgservers.schema.module.pool.pool.GetPoolRequest;
import com.omgservers.schema.module.pool.pool.GetPoolResponse;
import com.omgservers.schema.module.pool.poolServer.ViewPoolServersRequest;
import com.omgservers.schema.module.pool.poolServer.ViewPoolServersResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.pool.PoolDeletedEventBodyModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.operation.job.FindAndDeleteJobOperation;
import com.omgservers.service.shard.pool.PoolShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PoolDeletedEventHandlerImpl implements EventHandler {

    final PoolShard poolShard;

    final FindAndDeleteJobOperation findAndDeleteJobOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.POOL_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (PoolDeletedEventBodyModel) event.getBody();
        final var poolId = body.getId();

        return getPool(poolId)
                .flatMap(pool -> {
                    log.debug("Deleted, {}", pool);

                    return findAndDeleteJobOperation.execute(poolId)
                            .flatMap(voidItem -> viewPoolServers(poolId))
                            .invoke(poolServers -> {
                                if (poolServers.isEmpty()) {
                                    log.info("Pool \"{}\" deleted, no servers were found",
                                            poolId);
                                } else {
                                    log.error("Pool \"{}\" deleted, but there are still {} servers",
                                            poolId, poolServers.size());
                                }
                            });
                })
                .replaceWithVoid();
    }

    Uni<PoolModel> getPool(final Long id) {
        final var request = new GetPoolRequest(id);
        return poolShard.getService().execute(request)
                .map(GetPoolResponse::getPool);
    }

    Uni<List<PoolServerModel>> viewPoolServers(final Long poolId) {
        final var request = new ViewPoolServersRequest(poolId);
        return poolShard.getService().execute(request)
                .map(ViewPoolServersResponse::getPoolServers);
    }
}
