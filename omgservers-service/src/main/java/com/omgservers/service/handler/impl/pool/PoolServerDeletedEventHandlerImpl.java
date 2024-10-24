package com.omgservers.service.handler.impl.pool;

import com.omgservers.schema.model.poolServer.PoolServerModel;
import com.omgservers.schema.model.poolSeverContainer.PoolContainerModel;
import com.omgservers.schema.module.pool.poolContainer.DeletePoolContainerRequest;
import com.omgservers.schema.module.pool.poolContainer.DeletePoolContainerResponse;
import com.omgservers.schema.module.pool.poolContainer.ViewPoolContainersRequest;
import com.omgservers.schema.module.pool.poolContainer.ViewPoolContainersResponse;
import com.omgservers.schema.module.pool.poolServer.GetPoolServerRequest;
import com.omgservers.schema.module.pool.poolServer.GetPoolServerResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.pool.PoolServerDeletedEventBodyModel;
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
                    log.info("Deleted, {}", poolServer);

                    return deletePoolContainers(poolId, id);
                })
                .replaceWithVoid();
    }

    Uni<PoolServerModel> getPoolServer(final Long poolId, final Long id) {
        final var request = new GetPoolServerRequest(poolId, id);
        return poolModule.getPoolService().execute(request)
                .map(GetPoolServerResponse::getPoolServer);
    }

    Uni<Void> deletePoolContainers(final Long poolId, final Long serverId) {
        return viewPoolContainers(poolId, serverId)
                .flatMap(poolContainers -> Multi.createFrom().iterable(poolContainers)
                        .onItem().transformToUniAndConcatenate(poolContainer ->
                                deletePoolContainer(poolId, serverId, poolContainer.getId())
                                        .onFailure()
                                        .recoverWithItem(t -> {
                                            log.warn("Delete pool server container failed, id={}/{}/{}, {}:{}",
                                                    poolId,
                                                    serverId,
                                                    poolContainer.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<List<PoolContainerModel>> viewPoolContainers(final Long poolId,
                                                     final Long serverId) {
        final var request = new ViewPoolContainersRequest(poolId, serverId);
        return poolModule.getPoolService().execute(request)
                .map(ViewPoolContainersResponse::getPoolContainers);
    }

    Uni<Boolean> deletePoolContainer(final Long poolId,
                                     final Long serverId,
                                     final Long id) {
        final var request = new DeletePoolContainerRequest(poolId, serverId, id);
        return poolModule.getPoolService().execute(request)
                .map(DeletePoolContainerResponse::getDeleted);
    }
}
