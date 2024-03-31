package com.omgservers.service.handler.server;

import com.omgservers.model.dto.pool.SyncPoolServerRefRequest;
import com.omgservers.model.dto.pool.SyncPoolServerRefResponse;
import com.omgservers.model.dto.server.GetServerRequest;
import com.omgservers.model.dto.server.GetServerResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.module.server.ServerCreatedEventBodyModel;
import com.omgservers.model.server.ServerModel;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.pool.PoolServerRefModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.pool.PoolModule;
import com.omgservers.service.module.server.ServerModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ServerCreatedEventHandlerImpl implements EventHandler {

    final ServerModule serverModule;
    final PoolModule poolModule;

    final PoolServerRefModelFactory poolServerRefModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.SERVER_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (ServerCreatedEventBodyModel) event.getBody();
        final var serverId = body.getId();

        return getServer(serverId)
                .flatMap(server -> {
                    log.info("Server was created, server={}, uri={}", serverId, server.getUri());

                    final var idempotencyKey = event.getIdempotencyKey();

                    return syncPoolServerRef(server, idempotencyKey);
                })
                .replaceWithVoid();
    }

    Uni<ServerModel> getServer(final Long id) {
        final var request = new GetServerRequest(id);
        return serverModule.getServerService().getServer(request)
                .map(GetServerResponse::getServer);
    }

    Uni<Boolean> syncPoolServerRef(final ServerModel server, final String idempotencyKey) {
        final var poolId = server.getPoolId();
        final var serverId = server.getId();
        final var poolServerRef = poolServerRefModelFactory.create(poolId, serverId, idempotencyKey);
        final var request = new SyncPoolServerRefRequest(poolServerRef);
        return poolModule.getPoolService().syncPoolServerRef(request)
                .map(SyncPoolServerRefResponse::getCreated)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATION)) {
                            log.warn("Idempotency was violated, object={}, {}", poolServerRef, t.getMessage());
                            return Uni.createFrom().item(Boolean.FALSE);
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }
}
