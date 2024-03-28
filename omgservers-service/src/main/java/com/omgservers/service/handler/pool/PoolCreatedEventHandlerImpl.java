package com.omgservers.service.handler.pool;

import com.omgservers.model.dto.pool.GetPoolRequest;
import com.omgservers.model.dto.pool.GetPoolResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.module.pool.PoolCreatedEventBodyModel;
import com.omgservers.model.pool.PoolModel;
import com.omgservers.service.factory.PoolModelFactory;
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
public class PoolCreatedEventHandlerImpl implements EventHandler {

    final PoolModule poolModule;

    final PoolModelFactory poolModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.POOL_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (PoolCreatedEventBodyModel) event.getBody();
        final var poolId = body.getId();

        return getPool(poolId)
                .flatMap(pool -> {
                    log.info("Pool was created, pool={}", poolId);

                    return Uni.createFrom().voidItem();
                })
                .replaceWithVoid();
    }

    Uni<PoolModel> getPool(final Long id) {
        final var request = new GetPoolRequest(id);
        return poolModule.getPoolService().getPool(request)
                .map(GetPoolResponse::getPool);
    }
}
