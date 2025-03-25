package com.omgservers.service.service.task.impl.method.executeStageTask;

import com.omgservers.service.operation.job.ExecuteTaskOperation;
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

    final ExecuteTaskOperation executeTaskOperation;

    final StageTaskImpl stageTask;

    @Override
    public Uni<ExecuteStageTaskResponse> execute(final ExecuteStageTaskRequest request) {
        log.trace("{}", request);

        final var tenantId = request.getTenantId();
        final var tenantStageId = request.getTenantStageId();

        return executeTaskOperation.execute(stageTask.execute(tenantId, tenantStageId))
                .map(ExecuteStageTaskResponse::new);
    }
}
