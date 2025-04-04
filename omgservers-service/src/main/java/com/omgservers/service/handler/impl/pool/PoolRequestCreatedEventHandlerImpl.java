package com.omgservers.service.handler.impl.pool;

import com.omgservers.schema.model.poolRequest.PoolRequestModel;
import com.omgservers.schema.module.pool.poolRequest.GetPoolRequestRequest;
import com.omgservers.schema.module.pool.poolRequest.GetPoolRequestResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.pool.PoolRequestCreatedEventBodyModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.shard.pool.PoolShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PoolRequestCreatedEventHandlerImpl implements EventHandler {

    final PoolShard poolShard;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.POOL_REQUEST_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (PoolRequestCreatedEventBodyModel) event.getBody();
        final var poolId = body.getPoolId();
        final var id = body.getId();

        return getPoolRequest(poolId, id)
                .flatMap(poolRequest -> {
                    log.debug("Created, {}", poolRequest);
                    return Uni.createFrom().voidItem();
                })
                .replaceWithVoid();
    }

    Uni<PoolRequestModel> getPoolRequest(final Long poolId, final Long id) {
        final var request = new GetPoolRequestRequest(poolId, id);
        return poolShard.getPoolService().execute(request)
                .map(GetPoolRequestResponse::getPoolRequest);
    }
}
