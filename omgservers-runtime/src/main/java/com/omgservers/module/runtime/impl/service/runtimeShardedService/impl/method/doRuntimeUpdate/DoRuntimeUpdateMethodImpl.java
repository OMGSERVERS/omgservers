package com.omgservers.module.runtime.impl.service.runtimeShardedService.impl.method.doRuntimeUpdate;

import com.omgservers.dto.runtime.DoRuntimeUpdateShardedRequest;
import com.omgservers.dto.runtime.DoRuntimeUpdateShardedResponse;
import com.omgservers.dto.runtime.GetRuntimeShardedRequest;
import com.omgservers.dto.runtime.GetRuntimeShardedResponse;
import com.omgservers.factory.RuntimeCommandModelFactory;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandStatusEnum;
import com.omgservers.model.runtimeCommand.body.UpdateRuntimeCommandBodyModel;
import com.omgservers.module.context.ContextModule;
import com.omgservers.module.runtime.RuntimeModule;
import com.omgservers.module.runtime.impl.operation.handleRuntimeCommand.HandleRuntimeCommandOperation;
import com.omgservers.module.runtime.impl.operation.selectNewRuntimeCommands.SelectNewRuntimeCommandsOperation;
import com.omgservers.module.runtime.impl.operation.upsertRuntime.UpsertRuntimeOperation;
import com.omgservers.module.runtime.impl.operation.upsertRuntimeCommand.UpsertRuntimeCommandOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import com.omgservers.operation.getConfig.GetConfigOperation;
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

    final RuntimeModule runtimeModule;
    final ContextModule contextModule;

    final SelectNewRuntimeCommandsOperation selectNewRuntimeCommandsOperation;
    final UpsertRuntimeCommandOperation upsertRuntimeCommandOperation;
    final HandleRuntimeCommandOperation handleRuntimeCommandOperation;
    final UpsertRuntimeOperation upsertRuntimeOperation;
    final CheckShardOperation checkShardOperation;
    final GetConfigOperation getConfigOperation;

    final RuntimeCommandModelFactory runtimeCommandModelFactory;

    final PgPool pgPool;

    @Override
    public Uni<DoRuntimeUpdateShardedResponse> doRuntimeUpdate(final DoRuntimeUpdateShardedRequest request) {
        DoRuntimeUpdateShardedRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var runtimeId = request.getRuntimeId();
                    return getRuntime(runtimeId)
                            .flatMap(runtime -> updateRuntimeInTransaction(shardModel.shard(), runtime)
                                    .map(this::prepareMethodResponse));
                });
    }

    Uni<RuntimeModel> getRuntime(final Long id) {
        final var request = new GetRuntimeShardedRequest(id);
        return runtimeModule.getRuntimeShardedService().getRuntime(request)
                .map(GetRuntimeShardedResponse::getRuntime);
    }

    Uni<UpdateRuntimeResult> updateRuntimeInTransaction(final int shard,
                                                        final RuntimeModel runtime) {
        return pgPool.withTransaction(sqlConnection -> updateRuntime(sqlConnection, shard, runtime));
    }

    DoRuntimeUpdateShardedResponse prepareMethodResponse(UpdateRuntimeResult result) {
        final var affectedCommands = result.affectedCommands;
        final var doRuntimeUpdateShardedResponse = new DoRuntimeUpdateShardedResponse();
        doRuntimeUpdateShardedResponse.setHandledCommands(affectedCommands.size());
        if (getConfigOperation.getConfig().verbose()) {
            final var extendedResponse = new DoRuntimeUpdateShardedResponse.ExtendedResponse(affectedCommands);
            doRuntimeUpdateShardedResponse.setExtendedResponse(extendedResponse);
        }
        return doRuntimeUpdateShardedResponse;
    }

    Uni<UpdateRuntimeResult> updateRuntime(final SqlConnection sqlConnection,
                                           final int shard,
                                           final RuntimeModel runtime) {
        final var runtimeId = runtime.getId();
        runtime.setCurrentStep(runtime.getCurrentStep() + 1);
        return selectNewRuntimeCommandsOperation.selectNewRuntimeCommands(sqlConnection, shard, runtimeId)
                .flatMap(runtimeCommands -> {
                    final var updateRuntimeCommand = runtimeCommandModelFactory
                            .create(runtimeId, new UpdateRuntimeCommandBodyModel(runtime.getCurrentStep()));
                    // Every step enrich list by update command
                    runtimeCommands.add(updateRuntimeCommand);
                    return handleRuntimeCommands(sqlConnection, shard, runtimeCommands, runtime.getCurrentStep())
                            .flatMap(affectedCommands -> upsertRuntimeOperation.upsertRuntime(sqlConnection, shard, runtime)
                                    .map(inserted -> new UpdateRuntimeResult(affectedCommands)));
                });
    }

    Uni<List<RuntimeCommandModel>> handleRuntimeCommands(final SqlConnection sqlConnection,
                                                         final int shard,
                                                         final List<RuntimeCommandModel> runtimeCommands,
                                                         final Long currentStep) {
        return Multi.createFrom().iterable(runtimeCommands)
                .onItem().transformToUniAndConcatenate(runtimeCommand ->
                        handleRuntimeCommand(sqlConnection, shard, runtimeCommand, currentStep))
                .collect().asList();
    }

    Uni<RuntimeCommandModel> handleRuntimeCommand(final SqlConnection sqlConnection,
                                                  final int shard,
                                                  final RuntimeCommandModel runtimeCommand,
                                                  final Long currentStep) {
        return handleRuntimeCommandOperation.handleRuntimeCommand(runtimeCommand)
                .flatMap(result -> {
                    final var status = result ? RuntimeCommandStatusEnum.PROCESSED : RuntimeCommandStatusEnum.FAILED;
                    runtimeCommand.setStatus(status);
                    runtimeCommand.setStep(currentStep);
                    return upsertRuntimeCommandOperation.upsertRuntimeCommand(sqlConnection, shard, runtimeCommand)
                            .replaceWith(runtimeCommand);
                });
    }

    private record UpdateRuntimeResult(List<RuntimeCommandModel> affectedCommands) {
    }
}
