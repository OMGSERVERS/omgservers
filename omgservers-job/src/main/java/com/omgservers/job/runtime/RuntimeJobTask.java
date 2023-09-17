package com.omgservers.job.runtime;

import com.omgservers.dto.runtime.GetRuntimeRequest;
import com.omgservers.dto.runtime.GetRuntimeResponse;
import com.omgservers.dto.runtime.MarkRuntimeCommandsRequest;
import com.omgservers.dto.runtime.ViewRuntimeCommandsRequest;
import com.omgservers.dto.runtime.ViewRuntimeCommandsResponse;
import com.omgservers.dto.script.CallScriptRequest;
import com.omgservers.job.runtime.operation.mapRuntimeCommand.MapRuntimeCommandOperation;
import com.omgservers.model.job.JobTypeEnum;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandStatusEnum;
import com.omgservers.module.runtime.RuntimeModule;
import com.omgservers.module.script.ScriptModule;
import com.omgservers.module.system.impl.service.jobService.impl.JobTask;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RuntimeJobTask implements JobTask {

    final RuntimeModule runtimeModule;
    final ScriptModule scriptModule;

    final MapRuntimeCommandOperation mapRuntimeCommandOperation;

    @Override
    public JobTypeEnum getJobType() {
        return JobTypeEnum.RUNTIME;
    }

    @Override
    public Uni<Boolean> executeTask(final Long shardKey, final Long entityId) {
        return getRuntime(entityId)
                .flatMap(runtime ->
                        switch (runtime.getType()) {
                            case SCRIPT -> viewRuntimeCommands(runtime.getId())
                                    .call(runtimeCommands -> callScript(runtime, runtimeCommands))
                                    .call(runtimeCommands -> markRuntimeCommands(runtime, runtimeCommands));
                        })
                .replaceWith(true);
    }

    Uni<RuntimeModel> getRuntime(final Long id) {
        final var request = new GetRuntimeRequest(id);
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

    Uni<Void> markRuntimeCommands(final RuntimeModel runtime,
                                  final List<RuntimeCommandModel> runtimeCommands) {
        final var ids = runtimeCommands.stream().map(RuntimeCommandModel::getId).toList();
        final var request = new MarkRuntimeCommandsRequest(runtime.getId(), ids, RuntimeCommandStatusEnum.PROCESSED);
        return runtimeModule.getRuntimeService().markRuntimeCommands(request)
                .replaceWithVoid();
    }
}
