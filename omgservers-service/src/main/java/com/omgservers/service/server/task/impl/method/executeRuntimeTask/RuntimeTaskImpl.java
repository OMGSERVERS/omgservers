package com.omgservers.service.server.task.impl.method.executeRuntimeTask;

import com.omgservers.service.server.task.Task;
import com.omgservers.service.server.task.TaskResult;
import com.omgservers.service.server.task.impl.method.executeRuntimeTask.operation.DeleteInactiveClientsOperation;
import com.omgservers.service.server.task.impl.method.executeRuntimeTask.operation.FetchRuntimeOperation;
import com.omgservers.service.server.task.impl.method.executeRuntimeTask.operation.HandleRuntimeOperation;
import com.omgservers.service.server.task.impl.method.executeRuntimeTask.operation.SyncProducedDeploymentCommandsOperation;
import com.omgservers.service.server.task.impl.method.executeRuntimeTask.operation.SyncProducedMatchmakerCommandsOperation;
import com.omgservers.service.server.task.impl.method.executeRuntimeTask.operation.UpdateRuntimeOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RuntimeTaskImpl implements Task<RuntimeTaskArguments> {

    final HandleRuntimeOperation handleRuntimeOperation;
    final UpdateRuntimeOperation updateRuntimeOperation;
    final FetchRuntimeOperation fetchRuntimeOperation;

    final SyncProducedDeploymentCommandsOperation syncProducedDeploymentCommandsOperation;
    final SyncProducedMatchmakerCommandsOperation syncProducedMatchmakerCommandsOperation;
    final DeleteInactiveClientsOperation deleteInactiveClientsOperation;

    public Uni<TaskResult> execute(final RuntimeTaskArguments taskArguments) {
        final var runtimeId = taskArguments.runtimeId();
        return fetchRuntimeOperation.execute(runtimeId)
                .map(handleRuntimeOperation::execute)
                .call(handleRuntimeResult -> {
                    final var clientsToDelete = handleRuntimeResult.clientsToDelete();
                    final var deploymentCommandsToSync = handleRuntimeResult.deploymentCommandsToSync();
                    final var matchmakerCommandsToSync = handleRuntimeResult.matchmakerCommandsToSync();
                    return deleteInactiveClientsOperation.execute(clientsToDelete)
                            .flatMap(voidItem -> syncProducedDeploymentCommandsOperation
                                    .execute(deploymentCommandsToSync))
                            .flatMap(voidItem -> syncProducedMatchmakerCommandsOperation
                                    .execute(matchmakerCommandsToSync));
                })
                .flatMap(handleRuntimeResult -> {
                    final var runtimeChangeOfState = handleRuntimeResult.runtimeChangeOfState();
                    if (runtimeChangeOfState.isNotEmpty()) {
                        log.info("Update runtime state, runtimeId={}, {}",
                                runtimeId, runtimeChangeOfState);

                        return updateRuntimeOperation.execute(handleRuntimeResult)
                                .replaceWith(TaskResult.DONE);
                    } else {
                        return Uni.createFrom().item(TaskResult.NOOP);
                    }
                });
    }
}
