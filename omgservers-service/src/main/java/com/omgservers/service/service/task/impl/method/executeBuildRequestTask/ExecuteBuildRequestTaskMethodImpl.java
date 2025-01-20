package com.omgservers.service.service.task.impl.method.executeBuildRequestTask;

import com.omgservers.service.operation.job.test.ExecuteTaskOperation;
import com.omgservers.service.service.task.dto.ExecuteBuildRequestTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteBuildRequestTaskResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ExecuteBuildRequestTaskMethodImpl implements ExecuteBuildRequestTaskMethod {

    final ExecuteTaskOperation executeTaskOperation;

    final BuildRequestTaskImpl buildRequestTask;

    @Override
    public Uni<ExecuteBuildRequestTaskResponse> execute(final ExecuteBuildRequestTaskRequest request) {
        log.trace("{}", request);

        final var tenantId = request.getTenantId();
        final var buildRequestId = request.getTenantBuildRequestId();

        return executeTaskOperation.execute(buildRequestTask.execute(tenantId, buildRequestId))
                .map(ExecuteBuildRequestTaskResponse::new);
    }
}
