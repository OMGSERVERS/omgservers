package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtime.interchange;

import com.omgservers.model.dto.runtime.GetRuntimeRequest;
import com.omgservers.model.dto.runtime.GetRuntimeResponse;
import com.omgservers.model.dto.runtime.InterchangeRequest;
import com.omgservers.model.dto.runtime.InterchangeResponse;
import com.omgservers.model.outgoingCommand.OutgoingCommandModel;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.runtime.impl.operation.executeOutgoingCommand.ExecuteOutgoingCommandOperation;
import com.omgservers.service.module.runtime.impl.operation.runtime.updateRuntimeLastActivity.UpdateRuntimeLastActivityOperation;
import com.omgservers.service.module.runtime.impl.operation.runtimeCommand.deleteRuntimeCommandsByIds.DeleteRuntimeCommandByIdsOperation;
import com.omgservers.service.module.runtime.impl.operation.runtimeCommand.selectActiveRuntimeCommandsByRuntimeId.SelectActiveRuntimeCommandsByRuntimeIdOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
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

    final RuntimeModule runtimeModule;

    final SelectActiveRuntimeCommandsByRuntimeIdOperation selectActiveRuntimeCommandsByRuntimeIdOperation;
    final UpdateRuntimeLastActivityOperation updateRuntimeLastActivityOperation;
    final DeleteRuntimeCommandByIdsOperation deleteRuntimeCommandByIdsOperation;
    final ExecuteOutgoingCommandOperation executeOutgoingCommandOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<InterchangeResponse> interchange(final InterchangeRequest request) {
        log.debug("Interchange, request={}", request);

        final var fromUserId = request.getFromUserId();
        final var runtimeId = request.getRuntimeId();

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> getRuntime(runtimeId)
                        .flatMap(runtime -> {
                            final int shard = shardModel.shard();

                            if (runtime.getUserId().equals(fromUserId)) {
                                final var consumedCommands = request.getConsumedCommands();

                                if (runtime.getDeleted()) {
                                    throw new ServerSideNotFoundException(ExceptionQualifierEnum.RUNTIME_NOT_FOUND,
                                            "runtime was already deleted, runtimeId=" + runtimeId);
                                }

                                return handleCommands(runtimeId, request.getOutgoingCommands())
                                        .flatMap(voidItem -> receiveCommands(shard, runtimeId, consumedCommands));

                            } else {
                                throw new ServerSideBadRequestException(ExceptionQualifierEnum.RUNTIME_ID_WRONG,
                                        "wrong runtimeId, runtimeId=" + runtimeId);
                            }
                        })
                )
                .map(InterchangeResponse::new);
    }

    Uni<RuntimeModel> getRuntime(final Long id) {
        final var request = new GetRuntimeRequest(id);
        return runtimeModule.getRuntimeService().getRuntime(request)
                .map(GetRuntimeResponse::getRuntime);
    }

    Uni<Void> handleCommands(final Long runtimeId, final List<OutgoingCommandModel> outgoingCommands) {
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
                        updateRuntimeLastActivityOperation.updateRuntimeLastActivity(
                                        changeContext,
                                        sqlConnection,
                                        shard,
                                        runtimeId)
                                .flatMap(updated -> deleteRuntimeCommandByIdsOperation.deleteRuntimeCommandByIds(
                                        changeContext,
                                        sqlConnection,
                                        shard,
                                        runtimeId,
                                        consumedCommands))
                                .flatMap(deleted -> selectActiveRuntimeCommandsByRuntimeIdOperation
                                        .selectActiveRuntimeCommandsByRuntimeId(
                                                sqlConnection,
                                                shard,
                                                runtimeId))
                )
                .map(ChangeContext::getResult);
    }
}
