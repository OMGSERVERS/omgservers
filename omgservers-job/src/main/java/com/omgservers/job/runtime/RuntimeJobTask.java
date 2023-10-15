package com.omgservers.job.runtime;

import com.omgservers.dto.runtime.GetRuntimeRequest;
import com.omgservers.dto.runtime.GetRuntimeResponse;
import com.omgservers.dto.runtime.UpdateRuntimeCommandsStatusRequest;
import com.omgservers.dto.runtime.ViewRuntimeCommandsRequest;
import com.omgservers.dto.runtime.ViewRuntimeCommandsResponse;
import com.omgservers.dto.script.CallScriptRequest;
import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.job.runtime.operation.mapRuntimeCommand.MapRuntimeCommandOperation;
import com.omgservers.model.job.JobQualifierEnum;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandStatusEnum;
import com.omgservers.model.runtimeCommand.body.UpdateRuntimeCommandBodyModel;
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
public class RuntimeJobTask implements JobTask {

    final RuntimeModule runtimeModule;
    final ScriptModule scriptModule;

    final MapRuntimeCommandOperation mapRuntimeCommandOperation;
    final RuntimeCommandModelFactory runtimeCommandModelFactory;

    @Override
    public JobQualifierEnum getJobType() {
        return JobQualifierEnum.RUNTIME;
    }

    @Override
    public Uni<Void> executeTask(final Long shardKey, final Long entityId) {
        final var runtimeId = entityId;
        return getRuntime(runtimeId)
                .onFailure(ServerSideNotFoundException.class).recoverWithNull()
                .invoke(runtime -> {
                    if (Objects.isNull(runtime)) {
                        log.info("Runtime was not found, skip job execution, runtimeId={}", runtimeId);
                    }
                })
                .onItem().ifNotNull().transformToUni(runtime -> switch (runtime.getType()) {
                    case SCRIPT -> viewRuntimeCommands(runtime.getId())
                            .flatMap(runtimeCommands -> {
                                // Adding update runtime command automatically for every iteration
                                final var commandBody = UpdateRuntimeCommandBodyModel.builder()
                                        .time(Instant.now().toEpochMilli())
                                        .build();
                                final var updateRuntime = runtimeCommandModelFactory
                                        .create(runtime.getId(), commandBody);
                                runtimeCommands.add(updateRuntime);

                                return callScript(runtime, runtimeCommands)
                                        .call(voidItem -> updateRuntimeCommandsStatus(runtime,
                                                runtimeCommands));
                            });
                })
                .replaceWithVoid();
    }

    Uni<RuntimeModel> getRuntime(final Long id) {
        final var request = new GetRuntimeRequest(id, false);
        return runtimeModule.getRuntimeService().getRuntime(request)
                .map(GetRuntimeResponse::getRuntime);
    }

    Uni<List<RuntimeCommandModel>> viewRuntimeCommands(final Long runtimeId) {
        final var request = new ViewRuntimeCommandsRequest(runtimeId, RuntimeCommandStatusEnum.NEW);
        return runtimeModule.getRuntimeService().viewRuntimeCommands(request)
                .map(ViewRuntimeCommandsResponse::getRuntimeCommands);
    }

    Uni<Void> callScript(final RuntimeModel runtime, final List<RuntimeCommandModel> runtimeCommands) {
        final var scriptEvents = runtimeCommands.stream()
                .map(mapRuntimeCommandOperation::mapRuntimeCommand)
                .toList();

        final var callScriptRequest = new CallScriptRequest(runtime.getScriptId(), scriptEvents);
        return scriptModule.getScriptService().callScript(callScriptRequest)
                .replaceWithVoid();
    }

    Uni<Void> updateRuntimeCommandsStatus(final RuntimeModel runtime,
                                          final List<RuntimeCommandModel> runtimeCommands) {
        final var ids = runtimeCommands.stream().map(RuntimeCommandModel::getId).toList();
        final var request =
                new UpdateRuntimeCommandsStatusRequest(runtime.getId(), ids, RuntimeCommandStatusEnum.PROCESSED);
        return runtimeModule.getRuntimeService().updateRuntimeCommandsStatus(request)
                .replaceWithVoid();
    }
}
