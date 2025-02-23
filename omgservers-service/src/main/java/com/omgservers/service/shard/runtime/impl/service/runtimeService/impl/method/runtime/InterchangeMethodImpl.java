package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtime;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.outgoingCommand.OutgoingCommandModel;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.schema.module.runtime.GetRuntimeRequest;
import com.omgservers.schema.module.runtime.GetRuntimeResponse;
import com.omgservers.schema.module.runtime.InterchangeRequest;
import com.omgservers.schema.module.runtime.InterchangeResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.shard.runtime.RuntimeShard;
import com.omgservers.service.shard.runtime.impl.operation.executeOutgoingCommand.ExecuteOutgoingCommandOperation;
import com.omgservers.service.shard.runtime.impl.operation.runtime.UpdateRuntimeLastActivityOperation;
import com.omgservers.service.shard.runtime.impl.operation.runtimeCommand.DeleteRuntimeCommandByIdsOperation;
import com.omgservers.service.shard.runtime.impl.operation.runtimeCommand.SelectActiveRuntimeCommandsByRuntimeIdOperation;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class InterchangeMethodImpl implements InterchangeMethod {

    final RuntimeShard runtimeShard;

    final SelectActiveRuntimeCommandsByRuntimeIdOperation selectActiveRuntimeCommandsByRuntimeIdOperation;
    final UpdateRuntimeLastActivityOperation updateRuntimeLastActivityOperation;
    final DeleteRuntimeCommandByIdsOperation deleteRuntimeCommandByIdsOperation;
    final ExecuteOutgoingCommandOperation executeOutgoingCommandOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<InterchangeResponse> execute(final InterchangeRequest request) {
        log.trace("{}", request);

        final var runtimeId = request.getRuntimeId();

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> getRuntime(runtimeId)
                        .flatMap(runtime -> {
                            final int shard = shardModel.shard();

                            final var consumedCommands = request.getConsumedCommands();
                            if (runtime.getDeleted()) {
                                throw new ServerSideNotFoundException(ExceptionQualifierEnum.RUNTIME_NOT_FOUND,
                                        "runtime was already deleted, runtimeId=" + runtimeId);
                            }

                            return handleCommands(runtimeId, request.getOutgoingCommands())
                                    .flatMap(voidItem -> receiveCommands(shard, runtimeId, consumedCommands));
                        })
                )
                .map(InterchangeResponse::new);
    }

    Uni<RuntimeModel> getRuntime(final Long id) {
        final var request = new GetRuntimeRequest(id);
        return runtimeShard.getService().execute(request)
                .map(GetRuntimeResponse::getRuntime);
    }

    Uni<Void> handleCommands(final Long runtimeId,
                             final List<OutgoingCommandModel> outgoingCommands) {
        return Multi.createFrom().iterable(outgoingCommands)
                .onItem().transformToUniAndConcatenate(outgoingCommand -> executeOutgoingCommandOperation
                        .executeOutgoingCommand(runtimeId, outgoingCommand)
                        .onFailure()
                        .recoverWithUni(t -> {
                            log.warn("Outgoing command failed, " +
                                            "runtimeId={}, " +
                                            "outgoingCommand={}, " +
                                            "{}:{}",
                                    runtimeId,
                                    outgoingCommand,
                                    t.getClass().getSimpleName(),
                                    t.getMessage(),
                                    t);
                            return Uni.createFrom().voidItem();
                        }))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<List<RuntimeCommandModel>> receiveCommands(final int shard,
                                                   final Long runtimeId,
                                                   final List<Long> consumedCommands) {
        return changeWithContextOperation.<List<RuntimeCommandModel>>changeWithContext((changeContext, sqlConnection) ->
                        updateRuntimeLastActivityOperation.execute(
                                        changeContext,
                                        sqlConnection,
                                        shard,
                                        runtimeId)
                                .flatMap(updated -> deleteRuntimeCommandByIdsOperation.execute(
                                        changeContext,
                                        sqlConnection,
                                        shard,
                                        runtimeId,
                                        consumedCommands))
                                .flatMap(deleted -> selectActiveRuntimeCommandsByRuntimeIdOperation
                                        .execute(
                                                sqlConnection,
                                                shard,
                                                runtimeId))
                )
                .map(ChangeContext::getResult);
    }
}
