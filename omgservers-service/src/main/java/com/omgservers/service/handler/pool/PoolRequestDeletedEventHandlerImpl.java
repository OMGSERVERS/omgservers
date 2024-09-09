package com.omgservers.service.handler.pool;

import com.omgservers.schema.module.pool.poolRequest.GetPoolRequestRequest;
import com.omgservers.schema.module.pool.poolRequest.GetPoolRequestResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.pool.PoolRequestDeletedEventBodyModel;
import com.omgservers.schema.model.poolRequest.PoolRequestModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.pool.PoolModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PoolRequestDeletedEventHandlerImpl implements EventHandler {

    final PoolModule poolModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.POOL_REQUEST_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (PoolRequestDeletedEventBodyModel) event.getBody();
        final var poolId = body.getPoolId();
        final var id = body.getId();

        return getPoolRequest(poolId, id)
                .flatMap(server -> {
                    log.info("Pool request was deleted, id={}/{}", poolId, id);

                    return Uni.createFrom().voidItem();
                })
                .replaceWithVoid();
    }

    Uni<PoolRequestModel> getPoolRequest(final Long poolId, final Long id) {
        final var request = new GetPoolRequestRequest(poolId, id);
        return poolModule.getPoolService().getPoolRequest(request)
                .map(GetPoolRequestResponse::getPoolRequest);
    }
}