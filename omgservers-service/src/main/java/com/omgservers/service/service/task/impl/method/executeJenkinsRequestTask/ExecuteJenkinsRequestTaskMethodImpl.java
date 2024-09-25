package com.omgservers.service.service.task.impl.method.executeJenkinsRequestTask;

import com.omgservers.service.service.task.dto.ExecuteJenkinsRequestTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteJenkinsRequestTaskResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ExecuteJenkinsRequestTaskMethodImpl implements ExecuteJenkinsRequestTaskMethod {

    final JenkinsRequestTaskImpl jenkinsRequestTask;

    @Override
    public Uni<ExecuteJenkinsRequestTaskResponse> executeJenkinsRequestTask(
            final ExecuteJenkinsRequestTaskRequest request) {
        log.debug("Execute jenkins request task, request={}", request);

        final var tenantId = request.getTenantId();
        final var jenkinsRequestId = request.getJenkinsRequestId();

        return jenkinsRequestTask.executeTask(tenantId, jenkinsRequestId)
                .onFailure()
                .recoverWithUni(t -> {
                    log.warn("Job task failed, tenant={}, {}:{}",
                            tenantId, t.getClass().getSimpleName(), t.getMessage(), t);
                    return Uni.createFrom().item(Boolean.FALSE);
                })
                .map(ExecuteJenkinsRequestTaskResponse::new);
    }
}
