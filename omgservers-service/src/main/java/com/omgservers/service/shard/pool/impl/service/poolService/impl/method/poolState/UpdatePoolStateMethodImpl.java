package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolState;

import com.omgservers.schema.model.poolRequest.PoolRequestModel;
import com.omgservers.schema.model.poolServer.PoolServerModel;
import com.omgservers.schema.model.poolSeverContainer.PoolContainerModel;
import com.omgservers.schema.module.pool.poolState.UpdatePoolStateRequest;
import com.omgservers.schema.module.pool.poolState.UpdatePoolStateResponse;
import com.omgservers.service.shard.pool.impl.operation.poolContainer.DeletePoolContainerOperation;
import com.omgservers.service.shard.pool.impl.operation.poolContainer.UpsertPoolContainerOperation;
import com.omgservers.service.shard.pool.impl.operation.poolRequest.DeletePoolRequestOperation;
import com.omgservers.service.shard.pool.impl.operation.poolServer.DeletePoolServerOperation;
import com.omgservers.service.shard.pool.impl.operation.poolServer.UpsertPoolServerOperation;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpdatePoolStateMethodImpl implements UpdatePoolStateMethod {

    final UpsertPoolContainerOperation upsertPoolContainerOperation;
    final DeletePoolContainerOperation deletePoolContainerOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertPoolServerOperation upsertPoolServerOperation;
    final DeletePoolServerOperation deletePoolServerOperation;

    final DeletePoolRequestOperation deletePoolRequestOperation;

    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<UpdatePoolStateResponse> execute(final UpdatePoolStateRequest request) {
        log.trace("{}", request);

        final var poolChangeOfState = request.getPoolChangeOfState();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Void>changeWithContext(
                            (changeContext, sqlConnection) ->
                                    syncPoolServers(changeContext,
                                            sqlConnection,
                                            shard,
                                            poolChangeOfState.getServersToSync())
                                            .flatMap(voidItem -> deletePoolServers(changeContext,
                                                    sqlConnection,
                                                    shard,
                                                    poolChangeOfState.getServersToDelete()))
                                            .flatMap(voidItem -> syncPoolContainers(changeContext,
                                                    sqlConnection,
                                                    shard,
                                                    poolChangeOfState.getContainersToSync()))
                                            .flatMap(voidItem -> deletePoolContainers(changeContext,
                                                    sqlConnection,
                                                    shard,
                                                    poolChangeOfState.getContainersToDelete()))
                                            .flatMap(voidItem -> deletePoolRequests(changeContext,
                                                    sqlConnection,
                                                    shard,
                                                    poolChangeOfState.getRequestsToDelete()))
                    );
                })
                .replaceWith(Boolean.TRUE)
                .map(UpdatePoolStateResponse::new);
    }

    Uni<Void> syncPoolServers(final ChangeContext<?> changeContext,
                              final SqlConnection sqlConnection,
                              final int shard,
                              final Collection<PoolServerModel> poolServers) {
        return Multi.createFrom().iterable(poolServers)
                .onItem().transformToUniAndConcatenate(poolServer ->
                        upsertPoolServerOperation.execute(changeContext,
                                sqlConnection,
                                shard,
                                poolServer))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> deletePoolServers(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int shard,
                                final Collection<PoolServerModel> poolServers) {
        return Multi.createFrom().iterable(poolServers)
                .onItem().transformToUniAndConcatenate(poolServer ->
                        deletePoolServerOperation.execute(changeContext,
                                sqlConnection,
                                shard,
                                poolServer.getPoolId(),
                                poolServer.getId()))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> syncPoolContainers(final ChangeContext<?> changeContext,
                                 final SqlConnection sqlConnection,
                                 final int shard,
                                 final Collection<PoolContainerModel> poolContainers) {
        return Multi.createFrom().iterable(poolContainers)
                .onItem().transformToUniAndConcatenate(poolContainer ->
                        upsertPoolContainerOperation.execute(changeContext,
                                sqlConnection,
                                shard,
                                poolContainer))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> deletePoolContainers(final ChangeContext<?> changeContext,
                                   final SqlConnection sqlConnection,
                                   final int shard,
                                   final Collection<PoolContainerModel> poolContainers) {
        return Multi.createFrom().iterable(poolContainers)
                .onItem().transformToUniAndConcatenate(poolContainer ->
                        deletePoolContainerOperation.execute(changeContext,
                                sqlConnection,
                                shard,
                                poolContainer.getPoolId(),
                                poolContainer.getServerId(),
                                poolContainer.getId()))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> deletePoolRequests(final ChangeContext<?> changeContext,
                                 final SqlConnection sqlConnection,
                                 final int shard,
                                 final Collection<PoolRequestModel> poolRequests) {
        return Multi.createFrom().iterable(poolRequests)
                .onItem().transformToUniAndConcatenate(poolRequest ->
                        deletePoolRequestOperation.execute(changeContext,
                                sqlConnection,
                                shard,
                                poolRequest.getPoolId(),
                                poolRequest.getId()))
                .collect().asList()
                .replaceWithVoid();
    }
}
