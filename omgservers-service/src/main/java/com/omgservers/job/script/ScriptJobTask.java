package com.omgservers.job.script;

import com.omgservers.dto.runtime.DeleteRuntimeCommandsRequest;
import com.omgservers.dto.runtime.DeleteRuntimeCommandsResponse;
import com.omgservers.dto.runtime.ViewRuntimeCommandsRequest;
import com.omgservers.dto.runtime.ViewRuntimeCommandsResponse;
import com.omgservers.dto.script.CallScriptRequest;
import com.omgservers.dto.script.GetScriptRequest;
import com.omgservers.dto.script.GetScriptResponse;
import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.job.script.operation.mapRuntimeCommand.MapRuntimeCommandOperation;
import com.omgservers.model.job.JobQualifierEnum;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.body.UpdateRuntimeCommandBodyModel;
import com.omgservers.model.script.ScriptModel;
import com.omgservers.module.runtime.RuntimeModule;
import com.omgservers.module.runtime.factory.RuntimeCommandModelFactory;
import com.omgservers.module.script.ScriptModule;
import com.omgservers.module.system.impl.service.jobService.impl.JobTask;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ScriptJobTask implements JobTask {

    final RuntimeModule runtimeModule;
    final ScriptModule scriptModule;

    final MapRuntimeCommandOperation mapRuntimeCommandOperation;
    final RuntimeCommandModelFactory runtimeCommandModelFactory;

    @Override
    public JobQualifierEnum getJobQualifier() {
        return JobQualifierEnum.SCRIPT;
    }

    @Override
    public Uni<Void> executeTask(final Long shardKey, final Long entityId) {
        final var scriptId = entityId;
        return getScript(scriptId)
                .onFailure(ServerSideNotFoundException.class).recoverWithNull()
                .invoke(script -> {
                    if (Objects.isNull(script)) {
                        log.info("Script was not found, skip job execution, scriptId={}", scriptId);
                    }
                })
                .onItem().ifNotNull().transformToUni(script -> viewRuntimeCommands(script.getRuntimeId())
                        .flatMap(runtimeCommands -> {
                            // Adding update runtime command automatically for every iteration
                            final var commandBody = UpdateRuntimeCommandBodyModel.builder()
                                    .time(Instant.now().toEpochMilli())
                                    .build();
                            final var updateRuntime = runtimeCommandModelFactory
                                    .create(script.getRuntimeId(), commandBody);
                            runtimeCommands.add(updateRuntime);

                            return callScript(script, runtimeCommands)
                                    .call(voidItem -> deleteRuntimeCommands(script,
                                            runtimeCommands));
                        })
                )
                .replaceWithVoid();
    }

    Uni<ScriptModel> getScript(final Long id) {
        final var request = new GetScriptRequest(id);
        return scriptModule.getScriptService().getScript(request)
                .map(GetScriptResponse::getScript);
    }

    Uni<List<RuntimeCommandModel>> viewRuntimeCommands(final Long runtimeId) {
        final var request = new ViewRuntimeCommandsRequest(runtimeId, false);
        return runtimeModule.getRuntimeService().viewRuntimeCommands(request)
                .map(ViewRuntimeCommandsResponse::getRuntimeCommands);
    }

    Uni<Void> callScript(final ScriptModel script,
                         final List<RuntimeCommandModel> runtimeCommands) {
        final var scriptRequests = runtimeCommands.stream()
                .map(mapRuntimeCommandOperation::mapRuntimeCommand)
                .toList();

        final var callScriptRequest = new CallScriptRequest(script.getId(), scriptRequests);
        return scriptModule.getScriptService().callScript(callScriptRequest)
                .replaceWithVoid();
    }

    Uni<Boolean> deleteRuntimeCommands(final ScriptModel script,
                                       final List<RuntimeCommandModel> runtimeCommands) {
        final var ids = runtimeCommands.stream().map(RuntimeCommandModel::getId).toList();
        final var request = new DeleteRuntimeCommandsRequest(script.getRuntimeId(), ids);
        return runtimeModule.getRuntimeService().deleteRuntimeCommands(request)
                .map(DeleteRuntimeCommandsResponse::getDeleted);
    }
}
