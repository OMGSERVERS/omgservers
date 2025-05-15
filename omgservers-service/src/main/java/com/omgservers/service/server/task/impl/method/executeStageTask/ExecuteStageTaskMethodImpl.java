package com.omgservers.service.server.task.impl.method.executeStageTask;

import com.omgservers.service.operation.task.ExecuteTaskOperation;
import com.omgservers.service.server.task.dto.ExecuteStageTaskRequest;
import com.omgservers.service.server.task.dto.ExecuteStageTaskResponse;
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
        log.debug("{}", request);

        final var tenantId = request.getTenantId();
        final var tenantStageId = request.getTenantStageId();

        final var taskArguments = new StageTaskArguments(tenantId, tenantStageId);
        return executeTaskOperation.executeFailSafe(stageTask, taskArguments)
                .map(ExecuteStageTaskResponse::new);
    }
}
