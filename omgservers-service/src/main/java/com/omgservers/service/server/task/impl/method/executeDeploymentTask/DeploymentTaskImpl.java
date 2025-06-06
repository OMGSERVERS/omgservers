package com.omgservers.service.server.task.impl.method.executeDeploymentTask;

import com.omgservers.service.server.task.Task;
import com.omgservers.service.server.task.TaskResult;
import com.omgservers.service.server.task.impl.method.executeDeploymentTask.dto.FetchDeploymentResult;
import com.omgservers.service.server.task.impl.method.executeDeploymentTask.operation.FetchDeploymentOperation;
import com.omgservers.service.server.task.impl.method.executeDeploymentTask.operation.HandleDeploymentOperation;
import com.omgservers.service.server.task.impl.method.executeDeploymentTask.operation.UpdateDeploymentOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class DeploymentTaskImpl implements Task<DeploymentTaskArguments> {

    final HandleDeploymentOperation handleDeploymentOperation;
    final UpdateDeploymentOperation updateDeploymentOperation;
    final FetchDeploymentOperation fetchDeploymentOperation;

    public Uni<TaskResult> execute(final DeploymentTaskArguments taskArguments) {
        final var deploymentId = taskArguments.deploymentId();

        return fetchDeploymentOperation.execute(deploymentId)
                .flatMap(fetchDeploymentResult -> {
                    final var deleted = fetchDeploymentResult.deploymentState().getDeployment().getDeleted();
                    if (deleted) {
                        log.warn("Deployment \"{}\" deleted, skip task execution", deploymentId);
                        return Uni.createFrom().item(TaskResult.DONE);
                    } else {
                        final var status = fetchDeploymentResult.tenantDeploymentResource().getStatus();
                        return switch (status) {
                            case PENDING -> handlePendingDeployment(fetchDeploymentResult);
                            case CREATED -> handleCreatedDeployment(fetchDeploymentResult);
                            case CLOSED -> handleClosedDeployment(fetchDeploymentResult);
                        };
                    }
                });
    }

    Uni<TaskResult> handlePendingDeployment(final FetchDeploymentResult fetchDeploymentResult) {
        // TODO Introduce stage command -> create open deployment command
        return Uni.createFrom().item(TaskResult.DONE);
    }

    Uni<TaskResult> handleCreatedDeployment(final FetchDeploymentResult fetchDeploymentResult) {
        return handleDeployment(fetchDeploymentResult);
    }

    Uni<TaskResult> handleClosedDeployment(final FetchDeploymentResult fetchDeploymentResult) {
        return handleDeployment(fetchDeploymentResult);
    }

    Uni<TaskResult> handleDeployment(final FetchDeploymentResult fetchDeploymentResult) {
        final var deploymentId = fetchDeploymentResult.deploymentId();
        return Uni.createFrom().item(fetchDeploymentResult)
                .map(handleDeploymentOperation::execute)
                .flatMap(handleDeploymentResult -> {
                    final var deploymentChangeOfState = handleDeploymentResult.deploymentChangeOfState();
                    if (deploymentChangeOfState.isNotEmpty()) {
                        log.info("Update deployment state, deploymentId={}, {}",
                                deploymentId, deploymentChangeOfState);

                        return updateDeploymentOperation.execute(handleDeploymentResult)
                                .replaceWith(TaskResult.DONE);
                    } else {
                        return Uni.createFrom().item(TaskResult.NOOP);
                    }
                });
    }
}
