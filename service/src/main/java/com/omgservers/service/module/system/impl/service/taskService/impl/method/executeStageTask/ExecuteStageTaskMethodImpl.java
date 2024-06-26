package com.omgservers.service.module.system.impl.service.taskService.impl.method.executeStageTask;

import com.omgservers.model.dto.system.task.ExecuteStageTaskRequest;
import com.omgservers.model.dto.system.task.ExecuteStageTaskResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ExecuteStageTaskMethodImpl implements ExecuteStageTaskMethod {

    final StageTaskImpl stageTask;

    @Override
    public Uni<ExecuteStageTaskResponse> executeStageTask(final ExecuteStageTaskRequest request) {
        log.debug("Execute stage task, request={}", request);

        final var tenantId = request.getTenantId();
        final var stageId = request.getStageId();

        return stageTask.executeTask(tenantId, stageId)
                .onFailure()
                .recoverWithUni(t -> {
                    log.warn("Job task failed, stageId={}:{}, {}:{}",
                            tenantId, stageId, t.getClass().getSimpleName(), t.getMessage());
                    return Uni.createFrom().item(Boolean.FALSE);
                })
                .map(ExecuteStageTaskResponse::new);
    }
}
