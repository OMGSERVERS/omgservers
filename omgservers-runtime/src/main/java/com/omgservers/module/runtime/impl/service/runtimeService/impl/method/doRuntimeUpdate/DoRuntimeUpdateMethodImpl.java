package com.omgservers.module.runtime.impl.service.runtimeService.impl.method.doRuntimeUpdate;

import com.omgservers.dto.context.CreateLuaInstanceForRuntimeEventsRequest;
import com.omgservers.dto.context.CreateLuaInstanceForRuntimeEventsResponse;
import com.omgservers.dto.runtime.DoRuntimeUpdateRequest;
import com.omgservers.dto.runtime.DoRuntimeUpdateResponse;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandStatusEnum;
import com.omgservers.model.runtimeCommand.body.UpdateRuntimeCommandBodyModel;
import com.omgservers.module.context.ContextModule;
import com.omgservers.module.runtime.factory.RuntimeCommandModelFactory;
import com.omgservers.module.runtime.impl.operation.handleRuntimeCommand.HandleRuntimeCommandOperation;
import com.omgservers.module.runtime.impl.operation.selectNewRuntimeCommands.SelectNewRuntimeCommandsOperation;
import com.omgservers.module.runtime.impl.operation.selectRuntime.SelectRuntimeOperation;
import com.omgservers.module.runtime.impl.operation.updateRuntimeCommandStatusAndStep.UpdateRuntimeCommandStatusAndStepOperation;
import com.omgservers.module.runtime.impl.operation.updateRuntimeStepAndState.UpdateRuntimeStepAndStateOperation;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DoRuntimeUpdateMethodImpl implements DoRuntimeUpdateMethod {

    final ContextModule contextModule;

    final UpdateRuntimeCommandStatusAndStepOperation updateRuntimeCommandStatusAndStepOperation;
    final SelectNewRuntimeCommandsOperation selectNewRuntimeCommandsOperation;
    final UpdateRuntimeStepAndStateOperation updateRuntimeStepAndStateOperation;
    final HandleRuntimeCommandOperation handleRuntimeCommandOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final SelectRuntimeOperation selectRuntimeOperation;
    final CheckShardOperation checkShardOperation;

    final RuntimeCommandModelFactory runtimeCommandModelFactory;

    final PgPool pgPool;

    @Override
    public Uni<DoRuntimeUpdateResponse> doRuntimeUpdate(final DoRuntimeUpdateRequest request) {
        DoRuntimeUpdateRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var runtimeId = request.getRuntimeId();
                    return changeWithContextOperation.<UpdateRuntimeResult>changeWithContext((changeContext, sqlConnection) ->
                                    selectRuntimeOperation.selectRuntime(sqlConnection, shard, runtimeId)
                                            .flatMap(runtime -> updateRuntime(changeContext, sqlConnection, shard, runtime))
                            )
                            .map(ChangeContext::getResult);
                })
                .map(updateRuntimeResult -> {
                    final var handledCommands = updateRuntimeResult.affectedCommands.size();
                    return new DoRuntimeUpdateResponse(handledCommands);
                });
    }

    Uni<UpdateRuntimeResult> updateRuntime(final ChangeContext<?> changeContext,
                                           final SqlConnection sqlConnection,
                                           final int shard,
                                           final RuntimeModel runtime) {
        return createLuaInstance(runtime)
                .flatMap(luaInstanceWasCreated -> {
                    final var runtimeId = runtime.getId();
                    final var step = runtime.getStep() + 1;
                    return selectNewRuntimeCommandsOperation.selectNewRuntimeCommands(sqlConnection, shard, runtimeId)
                            .flatMap(runtimeCommands -> {
                                final var updateRuntimeCommand = runtimeCommandModelFactory
                                        .create(runtimeId, new UpdateRuntimeCommandBodyModel(step));
                                // Enrich list by update command every step automatically
                                runtimeCommands.add(updateRuntimeCommand);

                                return handleRuntimeCommands(changeContext, sqlConnection, shard, runtimeCommands, step)
                                        .call(affectedCommands -> updateRuntimeStepAndStateOperation
                                                .updateRuntimeStepAndState(
                                                        changeContext,
                                                        sqlConnection,
                                                        shard,
                                                        runtimeId,
                                                        step,
                                                        runtime.getState()))
                                        .map(UpdateRuntimeResult::new);
                            });
                });
    }

    Uni<Boolean> createLuaInstance(RuntimeModel runtime) {
        final var request = new CreateLuaInstanceForRuntimeEventsRequest(runtime);
        return contextModule.getContextService().createLuaInstanceForRuntimeEvents(request)
                .map(CreateLuaInstanceForRuntimeEventsResponse::getCreated);
    }

    Uni<List<RuntimeCommandModel>> handleRuntimeCommands(final ChangeContext<?> changeContext,
                                                         final SqlConnection sqlConnection,
                                                         final int shard,
                                                         final List<RuntimeCommandModel> runtimeCommands,
                                                         final Long currentStep) {
        return Multi.createFrom().iterable(runtimeCommands)
                .onItem().transformToUniAndConcatenate(runtimeCommand ->
                        handleRuntimeCommand(
                                changeContext,
                                sqlConnection,
                                shard,
                                runtimeCommand,
                                currentStep))
                .collect().asList();
    }

    Uni<RuntimeCommandModel> handleRuntimeCommand(final ChangeContext<?> changeContext,
                                                  final SqlConnection sqlConnection,
                                                  final int shard,
                                                  final RuntimeCommandModel runtimeCommand,
                                                  final Long currentStep) {
        return handleRuntimeCommandOperation.handleRuntimeCommand(runtimeCommand)
                .flatMap(result -> {
                    final var runtimeCommandId = runtimeCommand.getId();
                    final var status = result ? RuntimeCommandStatusEnum.PROCESSED : RuntimeCommandStatusEnum.FAILED;
                    return updateRuntimeCommandStatusAndStepOperation.updateRuntimeCommandStatusAndStep(
                                    changeContext,
                                    sqlConnection,
                                    shard,
                                    runtimeCommandId,
                                    status,
                                    currentStep)
                            .replaceWith(runtimeCommand);
                });
    }

    private record UpdateRuntimeResult(List<RuntimeCommandModel> affectedCommands) {
    }
}
