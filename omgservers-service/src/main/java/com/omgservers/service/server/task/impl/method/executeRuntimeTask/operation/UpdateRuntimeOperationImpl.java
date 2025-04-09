package com.omgservers.service.server.task.impl.method.executeRuntimeTask.operation;

import com.omgservers.schema.model.runtimeChangeOfState.RuntimeChangeOfStateDto;
import com.omgservers.schema.module.runtime.runtimeState.UpdateRuntimeStateRequest;
import com.omgservers.schema.module.runtime.runtimeState.UpdateRuntimeStateResponse;
import com.omgservers.service.server.task.impl.method.executeRuntimeTask.dto.HandleRuntimeResult;
import com.omgservers.service.shard.runtime.RuntimeShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class UpdateRuntimeOperationImpl implements UpdateRuntimeOperation {

    final RuntimeShard runtimeShard;

    final SyncProducedDeploymentCommandsOperation syncProducedDeploymentCommandsOperation;
    final SyncProducedMatchmakerCommandsOperation syncProducedMatchmakerCommandsOperation;
    final DeleteInactiveClientsOperation deleteInactiveClientsOperation;

    @Override
    public Uni<Void> execute(final HandleRuntimeResult handleRuntimeResult) {
        final var runtimeId = handleRuntimeResult.runtimeId();
        final var deploymentCommandsToSync = handleRuntimeResult.deploymentCommandsToSync();
        final var matchmakerCommandsToSync = handleRuntimeResult.matchmakerCommandsToSync();
        final var clientsToDelete = handleRuntimeResult.clientsToDelete();
        final var runtimeChangeOfState = handleRuntimeResult.runtimeChangeOfState();

        return deleteInactiveClientsOperation.execute(clientsToDelete)
                .flatMap(voidItem -> syncProducedDeploymentCommandsOperation.execute(deploymentCommandsToSync))
                .flatMap(voidItem -> syncProducedMatchmakerCommandsOperation.execute(matchmakerCommandsToSync))
                .flatMap(voidItem -> updateDeploymentState(runtimeId, runtimeChangeOfState))
                .replaceWithVoid();
    }

    Uni<Boolean> updateDeploymentState(final Long runtimeId,
                                       final RuntimeChangeOfStateDto runtimeChangeOfState) {
        final var request = new UpdateRuntimeStateRequest(runtimeId, runtimeChangeOfState);
        return runtimeShard.getService().execute(request)
                .map(UpdateRuntimeStateResponse::getUpdated);
    }
}
