package com.omgservers.service.server.task.impl.method.executeStageTask;

import com.omgservers.service.server.task.Task;
import com.omgservers.service.server.task.TaskResult;
import com.omgservers.service.server.task.impl.method.executeStageTask.operation.CloseTenantDeploymentResourcesOperation;
import com.omgservers.service.server.task.impl.method.executeStageTask.operation.DeleteTenantDeploymentResourcesOperation;
import com.omgservers.service.server.task.impl.method.executeStageTask.operation.FetchStageOperation;
import com.omgservers.service.server.task.impl.method.executeStageTask.operation.HandleStageOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class StageTaskImpl implements Task<StageTaskArguments> {

    final HandleStageOperation handleStageOperation;
    final FetchStageOperation fetchStageOperation;

    final DeleteTenantDeploymentResourcesOperation deleteTenantDeploymentResourcesOperation;
    final CloseTenantDeploymentResourcesOperation closeTenantDeploymentResourcesOperation;

    public Uni<TaskResult> execute(final StageTaskArguments taskArguments) {
        final var tenantId = taskArguments.tenantId();
        final var tenantStageId = taskArguments.tenantStageId();

        return fetchStageOperation.execute(tenantId, tenantStageId)
                .map(handleStageOperation::execute)
                .flatMap(handleStageResult -> {

                    final var tenantDeploymentResourcesToClose = handleStageResult.tenantDeploymentResourcesToClose();
                    final var tenantDeploymentResourcesToDelete = handleStageResult.tenantDeploymentResourcesToDelete();

                    return Uni.createFrom().voidItem()
                            .flatMap(voidItem -> closeTenantDeploymentResourcesOperation
                                    .execute(tenantDeploymentResourcesToClose))
                            .flatMap(voidItem -> deleteTenantDeploymentResourcesOperation
                                    .execute(tenantDeploymentResourcesToDelete))
                            .replaceWith(TaskResult.DONE);
                });
    }
}
