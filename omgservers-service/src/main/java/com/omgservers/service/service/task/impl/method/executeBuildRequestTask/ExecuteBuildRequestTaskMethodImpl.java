package com.omgservers.service.service.task.impl.method.executeBuildRequestTask;

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

    final BuildRequestTaskImpl buildRequestTask;

    @Override
    public Uni<ExecuteBuildRequestTaskResponse> execute(
            final ExecuteBuildRequestTaskRequest request) {
        log.trace("{}", request);

        final var tenantId = request.getTenantId();
        final var buildRequestId = request.getTenantBuildRequestId();

        return buildRequestTask.execute(tenantId, buildRequestId)
                .onFailure()
                .recoverWithUni(t -> {
                    log.warn("Job task failed, tenant={}, {}:{}",
                            tenantId, t.getClass().getSimpleName(), t.getMessage(), t);
                    return Uni.createFrom().item(Boolean.FALSE);
                })
                .map(ExecuteBuildRequestTaskResponse::new);
    }
}
