package com.omgservers.service.handler.root;

import com.omgservers.model.dto.pool.SyncPoolRequest;
import com.omgservers.model.dto.pool.SyncPoolResponse;
import com.omgservers.model.dto.root.GetRootRequest;
import com.omgservers.model.dto.root.GetRootResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.module.root.RootCreatedEventBodyModel;
import com.omgservers.model.root.RootModel;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.pool.PoolModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.pool.PoolModule;
import com.omgservers.service.module.root.RootModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RootCreatedEventHandlerImpl implements EventHandler {

    final RootModule rootModule;
    final PoolModule poolModule;

    final PoolModelFactory poolModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.ROOT_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (RootCreatedEventBodyModel) event.getBody();
        final var rootId = body.getId();

        return getRoot(rootId)
                .flatMap(root -> {
                    log.info("Root was created, root={}, defaultPoolId={}", rootId, root.getDefaultPoolId());

                    final var idempotencyKey = event.getIdempotencyKey();

                    return syncDefaultPool(root, idempotencyKey);
                })
                .replaceWithVoid();
    }

    Uni<RootModel> getRoot(final Long id) {
        final var request = new GetRootRequest(id);
        return rootModule.getRootService().getRoot(request)
                .map(GetRootResponse::getRoot);
    }

    Uni<Boolean> syncDefaultPool(final RootModel root, final String idempotencyKey) {
        final var pool = poolModelFactory.create(root.getDefaultPoolId(), root.getId(), idempotencyKey);

        final var request = new SyncPoolRequest(pool);
        return poolModule.getPoolService().syncPool(request)
                .map(SyncPoolResponse::getCreated)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATION)) {
                            log.warn("Idempotency was violated, object={}, {}", pool, t.getMessage());
                            return Uni.createFrom().item(Boolean.FALSE);
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }
}
