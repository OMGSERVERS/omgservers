package com.omgservers.service.service.task.impl.method.executeStageTask;

import com.omgservers.service.service.task.dto.ExecuteStageTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteStageTaskResponse;
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
        log.debug("Requested, {}", request);

        final var tenantId = request.getTenantId();
        final var tenantStageId = request.getTenantStageId();

        return stageTask.executeTask(tenantId, tenantStageId)
                .onFailure()
                .recoverWithUni(t -> {
                    log.warn("Job task failed, tenantStage={}:{}, {}:{}",
                            tenantId, tenantStageId, t.getClass().getSimpleName(), t.getMessage(), t);
                    return Uni.createFrom().item(Boolean.FALSE);
                })
                .map(ExecuteStageTaskResponse::new);
    }
}
