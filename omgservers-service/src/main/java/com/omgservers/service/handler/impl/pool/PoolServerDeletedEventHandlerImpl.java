package com.omgservers.service.handler.impl.pool;

import com.omgservers.schema.module.pool.poolServer.GetPoolServerRequest;
import com.omgservers.schema.module.pool.poolServer.GetPoolServerResponse;
import com.omgservers.schema.module.pool.poolServerContainer.DeletePoolServerContainerRequest;
import com.omgservers.schema.module.pool.poolServerContainer.DeletePoolServerContainerResponse;
import com.omgservers.schema.module.pool.poolServerContainer.ViewPoolServerContainersRequest;
import com.omgservers.schema.module.pool.poolServerContainer.ViewPoolServerContainersResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.pool.PoolServerDeletedEventBodyModel;
import com.omgservers.schema.model.poolServer.PoolServerModel;
import com.omgservers.schema.model.poolSeverContainer.PoolServerContainerModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.pool.PoolModule;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PoolServerDeletedEventHandlerImpl implements EventHandler {

    final PoolModule poolModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.POOL_SERVER_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (PoolServerDeletedEventBodyModel) event.getBody();
        final var poolId = body.getPoolId();
        final var id = body.getId();

        return getPoolServer(poolId, id)
                .flatMap(poolServer -> {
                    log.info("Pool server was deleted, id={}/{}", poolId, id);

                    return deletePoolServerContainers(poolId, id);
                })
                .replaceWithVoid();
    }

    Uni<PoolServerModel> getPoolServer(final Long poolId, final Long id) {
        final var request = new GetPoolServerRequest(poolId, id);
        return poolModule.getService().getPoolServer(request)
                .map(GetPoolServerResponse::getPoolServer);
    }

    Uni<Void> deletePoolServerContainers(final Long poolId, final Long serverId) {
        return viewPoolServerContainers(poolId, serverId)
                .flatMap(poolServerContainers -> Multi.createFrom().iterable(poolServerContainers)
                        .onItem().transformToUniAndConcatenate(poolServerContainer ->
                                deletePoolServerContainer(poolId, serverId, poolServerContainer.getId())
                                        .onFailure()
                                        .recoverWithItem(t -> {
                                            log.warn("Delete pool server container failed, id={}/{}/{}, {}:{}",
                                                    poolId,
                                                    serverId,
                                                    poolServerContainer.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<List<PoolServerContainerModel>> viewPoolServerContainers(final Long poolId,
                                                                 final Long serverId) {
        final var request = new ViewPoolServerContainersRequest(poolId, serverId);
        return poolModule.getService().viewPoolServerContainers(request)
                .map(ViewPoolServerContainersResponse::getPoolServerContainers);
    }

    Uni<Boolean> deletePoolServerContainer(final Long poolId,
                                           final Long serverId,
                                           final Long id) {
        final var request = new DeletePoolServerContainerRequest(poolId, serverId, id);
        return poolModule.getService().deletePoolServerContainer(request)
                .map(DeletePoolServerContainerResponse::getDeleted);
    }
}
