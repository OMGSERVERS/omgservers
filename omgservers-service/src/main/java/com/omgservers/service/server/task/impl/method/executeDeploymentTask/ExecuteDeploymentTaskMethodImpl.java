package com.omgservers.service.server.task.impl.method.executeDeploymentTask;

import com.omgservers.service.operation.job.ExecuteTaskOperation;
import com.omgservers.service.server.task.dto.ExecuteDeploymentTaskRequest;
import com.omgservers.service.server.task.dto.ExecuteDeploymentTaskResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ExecuteDeploymentTaskMethodImpl implements ExecuteDeploymentTaskMethod {

    final ExecuteTaskOperation executeTaskOperation;

    final DeploymentTaskImpl deploymentTask;

    @Override
    public Uni<ExecuteDeploymentTaskResponse> execute(final ExecuteDeploymentTaskRequest request) {
        log.trace("{}", request);

        final var deploymentId = request.getDeploymentId();

        final var taskArguments = new DeploymentTaskArguments(deploymentId);
        return executeTaskOperation.executeFailSafe(deploymentTask, taskArguments)
                .map(ExecuteDeploymentTaskResponse::new);
    }
}
