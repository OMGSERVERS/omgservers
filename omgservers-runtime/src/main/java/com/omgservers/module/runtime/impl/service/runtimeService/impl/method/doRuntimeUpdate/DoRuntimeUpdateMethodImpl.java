package com.omgservers.module.runtime.impl.service.runtimeService.impl.method.doRuntimeUpdate;

import com.omgservers.dto.runtime.DoRuntimeUpdateRequest;
import com.omgservers.dto.runtime.DoRuntimeUpdateResponse;
import com.omgservers.dto.script.CallScriptRequest;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandStatusEnum;
import com.omgservers.model.runtimeCommand.body.UpdateRuntimeCommandBodyModel;
import com.omgservers.module.runtime.factory.RuntimeCommandModelFactory;
import com.omgservers.module.runtime.impl.operation.mapRuntimeCommand.MapRuntimeCommandOperation;
import com.omgservers.module.runtime.impl.operation.selectRuntime.SelectRuntimeOperation;
import com.omgservers.module.runtime.impl.operation.selectRuntimeCommandsByRuntimeIdAndStatus.SelectRuntimeCommandsByRuntimeIdAndStatusOperation;
import com.omgservers.module.runtime.impl.operation.updateRuntimeCommandsStatusAndStepByIds.UpdateRuntimeCommandStatusAndStepByIdsOperation;
import com.omgservers.module.runtime.impl.operation.updateRuntimeStep.UpdateRuntimeStepOperation;
import com.omgservers.module.script.ScriptModule;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
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

    final ScriptModule scriptModule;


    final SelectRuntimeCommandsByRuntimeIdAndStatusOperation selectRuntimeCommandsByRuntimeIdAndStatusOperation;
    final UpdateRuntimeCommandStatusAndStepByIdsOperation updateRuntimeCommandStatusAndStepByIdsOperation;
    final UpdateRuntimeStepOperation updateRuntimeStepOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final MapRuntimeCommandOperation mapRuntimeCommandOperation;
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
                    return changeWithContextOperation.<Void>changeWithContext(
                                    (changeContext, sqlConnection) -> selectRuntimeOperation
                                            .selectRuntime(sqlConnection, shard, runtimeId)
                                            .flatMap(runtime -> updateRuntime(
                                                    changeContext,
                                                    sqlConnection,
                                                    shard,
                                                    runtime)))
                            .map(ChangeContext::getResult);
                })
                .replaceWith(new DoRuntimeUpdateResponse());
    }

    Uni<Void> updateRuntime(final ChangeContext<?> changeContext,
                            final SqlConnection sqlConnection,
                            final int shard,
                            final RuntimeModel runtime) {
        final var runtimeId = runtime.getId();
        final var step = runtime.getStep() + 1;
        return selectRuntimeCommandsByRuntimeIdAndStatusOperation
                .selectRuntimeCommandsByRuntimeIdAndStatus(sqlConnection, shard, runtimeId,
                        RuntimeCommandStatusEnum.NEW)
                .flatMap(runtimeCommands -> {
                    final var updateRuntimeCommand = runtimeCommandModelFactory
                            .create(runtimeId, new UpdateRuntimeCommandBodyModel(step));
                    // Enrich list by update command every step automatically
                    runtimeCommands.add(updateRuntimeCommand);

                    return handleRuntimeCommands(runtime, runtimeCommands)
                            .flatMap(voidItem -> {
                                final var ids = runtimeCommands.stream().map(RuntimeCommandModel::getId).toList();
                                return updateRuntimeCommandStatusAndStepByIdsOperation
                                        .updateRuntimeCommandStatusAndStepByIds(changeContext, sqlConnection, shard,
                                                ids, RuntimeCommandStatusEnum.PROCESSED, step);
                            });
                })
                .call(voidItem -> updateRuntimeStepOperation
                        .updateRuntimeStep(changeContext, sqlConnection, shard, runtimeId, step))
                .replaceWithVoid();
    }

    Uni<Void> handleRuntimeCommands(final RuntimeModel runtime, final List<RuntimeCommandModel> runtimeCommands) {
        return switch (runtime.getType()) {
            case SCRIPT -> callScript(runtime, runtimeCommands);
        };
    }

    Uni<Void> callScript(final RuntimeModel runtime, final List<RuntimeCommandModel> runtimeCommands) {
        final var scriptEvents = runtimeCommands.stream()
                .map(mapRuntimeCommandOperation::mapRuntimeCommand)
                .toList();

        final var callScriptRequest = new CallScriptRequest(runtime.getScriptId(), scriptEvents);
        return scriptModule.getScriptService().callScript(callScriptRequest)
                .replaceWithVoid();
    }
}
