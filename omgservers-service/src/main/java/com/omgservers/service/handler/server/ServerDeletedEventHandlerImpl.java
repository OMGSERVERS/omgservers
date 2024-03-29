package com.omgservers.service.handler.server;

import com.omgservers.model.dto.pool.DeletePoolServerRefRequest;
import com.omgservers.model.dto.pool.DeletePoolServerRefResponse;
import com.omgservers.model.dto.pool.FindPoolServerRefRequest;
import com.omgservers.model.dto.pool.FindPoolServerRefResponse;
import com.omgservers.model.dto.server.GetServerRequest;
import com.omgservers.model.dto.server.GetServerResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.module.server.ServerDeletedEventBodyModel;
import com.omgservers.model.poolServerRef.PoolServerRefModel;
import com.omgservers.model.server.ServerModel;
import com.omgservers.service.exception.ServerSideNotFoundException;
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
public class ServerDeletedEventHandlerImpl implements EventHandler {

    final ServerModule serverModule;
    final PoolModule poolModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.SERVER_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (ServerDeletedEventBodyModel) event.getBody();
        final var serverId = body.getId();

        return getServer(serverId)
                .flatMap(server -> {
                    log.info("Server was deleted, server={}, uri={}", serverId, server.getUri());

                    final var poolId = server.getPoolId();
                    return findAndDeletePoolServerRef(poolId, serverId);
                })
                .replaceWithVoid();
    }

    Uni<ServerModel> getServer(final Long id) {
        final var request = new GetServerRequest(id);
        return serverModule.getServerService().getServer(request)
                .map(GetServerResponse::getServer);
    }

    Uni<Void> findAndDeletePoolServerRef(final Long poolId, final Long serverId) {
        return findPoolServerRef(poolId, serverId)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithNull()
                .onItem().ifNotNull().transformToUni(poolServerRef ->
                        deletePoolServerRef(serverId, poolServerRef.getId()))
                .replaceWithVoid();
    }

    Uni<PoolServerRefModel> findPoolServerRef(final Long poolId, final Long serverId) {
        final var request = new FindPoolServerRefRequest(poolId, serverId);
        return poolModule.getPoolService().findPoolServerRef(request)
                .map(FindPoolServerRefResponse::getPoolServerRef);
    }

    Uni<Boolean> deletePoolServerRef(final Long poolId, final Long id) {
        final var request = new DeletePoolServerRefRequest(poolId, id);
        return poolModule.getPoolService().deletePoolServerRef(request)
                .map(DeletePoolServerRefResponse::getDeleted);
    }
}
