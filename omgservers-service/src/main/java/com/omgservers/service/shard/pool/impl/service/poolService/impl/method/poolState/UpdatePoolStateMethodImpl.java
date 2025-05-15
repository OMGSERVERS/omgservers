package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolState;

import com.omgservers.schema.model.poolContainer.PoolContainerModel;
import com.omgservers.schema.model.poolServer.PoolServerModel;
import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.pool.poolState.UpdatePoolStateRequest;
import com.omgservers.schema.shard.pool.poolState.UpdatePoolStateResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.pool.impl.operation.poolCommand.DeletePoolCommandOperation;
import com.omgservers.service.shard.pool.impl.operation.poolContainer.DeletePoolContainerOperation;
import com.omgservers.service.shard.pool.impl.operation.poolContainer.UpsertPoolContainerOperation;
import com.omgservers.service.shard.pool.impl.operation.poolRequest.DeletePoolRequestOperation;
import com.omgservers.service.shard.pool.impl.operation.poolServer.DeletePoolServerOperation;
import com.omgservers.service.shard.pool.impl.operation.poolServer.UpsertPoolServerOperation;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpdatePoolStateMethodImpl implements UpdatePoolStateMethod {

    final UpsertPoolContainerOperation upsertPoolContainerOperation;
    final DeletePoolContainerOperation deletePoolContainerOperation;
    final DeletePoolRequestOperation deletePoolRequestOperation;
    final DeletePoolCommandOperation deletePoolCommandOperation;
    final UpsertPoolServerOperation upsertPoolServerOperation;
    final DeletePoolServerOperation deletePoolServerOperation;

    final ChangeWithContextOperation changeWithContextOperation;

    @Override
    public Uni<UpdatePoolStateResponse> execute(final ShardModel shardModel,
                                                final UpdatePoolStateRequest request) {
        log.debug("{}", request);

        final var poolChangeOfState = request.getPoolChangeOfState();
        final var slot = shardModel.slot();
        final var poolId = request.getPoolId();
        return changeWithContextOperation.<Void>changeWithContext((changeContext, sqlConnection) ->
                        deletePoolCommands(changeContext,
                                sqlConnection,
                                slot,
                                poolId,
                                poolChangeOfState.getPoolCommandsToDelete())
                                .flatMap(voidItem -> deletePoolRequest(changeContext,
                                        sqlConnection,
                                        slot,
                                        poolId,
                                        poolChangeOfState.getPoolRequestsToDelete()))
                                .flatMap(voidItem -> syncPoolServers(changeContext,
                                        sqlConnection,
                                        slot,
                                        poolChangeOfState.getPoolServersToSync()))
                                .flatMap(voidItem -> deletePoolServers(changeContext,
                                        sqlConnection,
                                        slot,
                                        poolId,
                                        poolChangeOfState.getPoolServersToDelete()))
                                .flatMap(voidItem -> syncPoolContainers(changeContext,
                                        sqlConnection,
                                        slot,
                                        poolChangeOfState.getPoolContainersToSync()))
                                .flatMap(voidItem -> deletePoolContainers(changeContext,
                                        sqlConnection,
                                        slot,
                                        poolId,
                                        poolChangeOfState.getPoolContainersToDelete())))
                .replaceWith(Boolean.TRUE)
                .map(UpdatePoolStateResponse::new);
    }

    Uni<Void> deletePoolCommands(final ChangeContext<?> changeContext,
                                 final SqlConnection sqlConnection,
                                 final int slot,
                                 final Long poolId,
                                 final List<Long> poolCommands) {
        return Multi.createFrom().iterable(poolCommands)
                .onItem().transformToUniAndConcatenate(poolCommandId ->
                        deletePoolCommandOperation.execute(changeContext,
                                sqlConnection,
                                slot,
                                poolId,
                                poolCommandId))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> deletePoolRequest(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int slot,
                                final Long poolId,
                                final List<Long> poolRequests) {
        return Multi.createFrom().iterable(poolRequests)
                .onItem().transformToUniAndConcatenate(poolRequestId ->
                        deletePoolRequestOperation.execute(changeContext,
                                sqlConnection,
                                slot,
                                poolId,
                                poolRequestId))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> syncPoolServers(final ChangeContext<?> changeContext,
                              final SqlConnection sqlConnection,
                              final int slot,
                              final List<PoolServerModel> poolServers) {
        return Multi.createFrom().iterable(poolServers)
                .onItem().transformToUniAndConcatenate(poolServer ->
                        upsertPoolServerOperation.execute(changeContext,
                                sqlConnection,
                                slot,
                                poolServer))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> deletePoolServers(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int slot,
                                final Long poolId,
                                final List<Long> poolServers) {
        return Multi.createFrom().iterable(poolServers)
                .onItem().transformToUniAndConcatenate(poolServerId ->
                        deletePoolServerOperation.execute(changeContext,
                                sqlConnection,
                                slot,
                                poolId,
                                poolServerId))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> syncPoolContainers(final ChangeContext<?> changeContext,
                                 final SqlConnection sqlConnection,
                                 final int slot,
                                 final List<PoolContainerModel> poolContainers) {
        return Multi.createFrom().iterable(poolContainers)
                .onItem().transformToUniAndConcatenate(poolContainer ->
                        upsertPoolContainerOperation.execute(changeContext,
                                sqlConnection,
                                slot,
                                poolContainer))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> deletePoolContainers(final ChangeContext<?> changeContext,
                                   final SqlConnection sqlConnection,
                                   final int slot,
                                   final Long poolId,
                                   final List<Long> poolContainers) {
        return Multi.createFrom().iterable(poolContainers)
                .onItem().transformToUniAndConcatenate(poolContainerId ->
                        deletePoolContainerOperation.execute(changeContext,
                                sqlConnection,
                                slot,
                                poolId,
                                poolContainerId))
                .collect().asList()
                .replaceWithVoid();
    }
}
