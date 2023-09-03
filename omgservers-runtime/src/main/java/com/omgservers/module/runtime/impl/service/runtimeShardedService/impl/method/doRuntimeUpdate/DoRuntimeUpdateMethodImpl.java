package com.omgservers.module.runtime.impl.service.runtimeShardedService.impl.method.doRuntimeUpdate;

import com.omgservers.ChangeContext;
import com.omgservers.dto.runtime.DoRuntimeUpdateShardedRequest;
import com.omgservers.dto.runtime.DoRuntimeUpdateShardedResponse;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandStatusEnum;
import com.omgservers.model.runtimeCommand.body.UpdateRuntimeCommandBodyModel;
import com.omgservers.module.runtime.factory.RuntimeCommandModelFactory;
import com.omgservers.module.runtime.impl.operation.handleRuntimeCommand.HandleRuntimeCommandOperation;
import com.omgservers.module.runtime.impl.operation.selectNewRuntimeCommands.SelectNewRuntimeCommandsOperation;
import com.omgservers.module.runtime.impl.operation.selectRuntime.SelectRuntimeOperation;
import com.omgservers.module.runtime.impl.operation.updateRuntimeCommandStatusAndStep.UpdateRuntimeCommandStatusAndStepOperation;
import com.omgservers.module.runtime.impl.operation.updateRuntimeCurrentStep.UpdateRuntimeCurrentStepOperation;
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

    final UpdateRuntimeCommandStatusAndStepOperation updateRuntimeCommandStatusAndStepOperation;
    final SelectNewRuntimeCommandsOperation selectNewRuntimeCommandsOperation;
    final UpdateRuntimeCurrentStepOperation updateRuntimeCurrentStepOperation;
    final HandleRuntimeCommandOperation handleRuntimeCommandOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final SelectRuntimeOperation selectRuntimeOperation;
    final CheckShardOperation checkShardOperation;

    final RuntimeCommandModelFactory runtimeCommandModelFactory;

    final PgPool pgPool;

    @Override
    public Uni<DoRuntimeUpdateShardedResponse> doRuntimeUpdate(final DoRuntimeUpdateShardedRequest request) {
        DoRuntimeUpdateShardedRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var runtimeId = request.getRuntimeId();
                    return changeWithContextOperation.changeWithContext((changeContext, sqlConnection) ->
                            selectRuntimeOperation.selectRuntime(sqlConnection, shard, runtimeId)
                                    .flatMap(runtime -> updateRuntime(changeContext, sqlConnection, shard, runtime))
                    );
                })
                .map(updateRuntimeResult -> {
                    final var handledCommands = updateRuntimeResult.affectedCommands.size();
                    return new DoRuntimeUpdateShardedResponse(handledCommands);
                });
    }

    Uni<UpdateRuntimeResult> updateRuntime(final ChangeContext changeContext,
                                           final SqlConnection sqlConnection,
                                           final int shard,
                                           final RuntimeModel runtime) {
        final var runtimeId = runtime.getId();
        final var currentStep = runtime.getCurrentStep() + 1;
        return selectNewRuntimeCommandsOperation.selectNewRuntimeCommands(sqlConnection, shard, runtimeId)
                .flatMap(runtimeCommands -> {
                    final var updateRuntimeCommand = runtimeCommandModelFactory
                            .create(runtimeId, new UpdateRuntimeCommandBodyModel(currentStep));
                    // Enrich list by update command every step automatically
                    runtimeCommands.add(updateRuntimeCommand);

                    return updateRuntimeCurrentStepOperation.updateRuntimeCurrentStep(
                                    changeContext,
                                    sqlConnection,
                                    shard,
                                    runtimeId,
                                    currentStep)
                            .flatMap(runtimeWasUpdated -> handleRuntimeCommands(
                                    changeContext,
                                    sqlConnection,
                                    shard,
                                    runtimeCommands,
                                    currentStep).map(UpdateRuntimeResult::new));
                });
    }

    Uni<List<RuntimeCommandModel>> handleRuntimeCommands(final ChangeContext changeContext,
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

    Uni<RuntimeCommandModel> handleRuntimeCommand(final ChangeContext changeContext,
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
