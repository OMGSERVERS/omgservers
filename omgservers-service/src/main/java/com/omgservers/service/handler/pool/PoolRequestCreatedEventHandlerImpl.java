package com.omgservers.service.handler.pool;

import com.omgservers.model.dto.pool.poolRequest.GetPoolRequestRequest;
import com.omgservers.model.dto.pool.poolRequest.GetPoolRequestResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.module.pool.PoolRequestCreatedEventBodyModel;
import com.omgservers.model.poolRequest.PoolRequestModel;
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
public class PoolRequestCreatedEventHandlerImpl implements EventHandler {

    final PoolModule poolModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.POOL_REQUEST_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (PoolRequestCreatedEventBodyModel) event.getBody();
        final var poolId = body.getPoolId();
        final var id = body.getId();

        return getPoolRequest(poolId, id)
                .flatMap(server -> {
                    log.info("Pool request was created, id={}/{}", poolId, id);

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
