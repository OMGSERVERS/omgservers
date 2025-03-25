package com.omgservers.service.service.task.impl.method.executeDeploymentTask;

import com.omgservers.service.service.task.impl.method.executeDeploymentTask.operation.FetchDeploymentOperation;
import com.omgservers.service.service.task.impl.method.executeDeploymentTask.operation.HandleDeploymentOperation;
import com.omgservers.service.service.task.impl.method.executeDeploymentTask.operation.UpdateDeploymentOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class DeploymentTaskImpl {

    final HandleDeploymentOperation handleDeploymentOperation;
    final UpdateDeploymentOperation updateDeploymentOperation;
    final FetchDeploymentOperation fetchDeploymentOperation;

    public Uni<Boolean> execute(final Long deploymentId) {
        return fetchDeploymentOperation.execute(deploymentId)
                .map(handleDeploymentOperation::execute)
                .flatMap(updateDeploymentOperation::execute)
                .replaceWith(Boolean.TRUE);
    }
}
