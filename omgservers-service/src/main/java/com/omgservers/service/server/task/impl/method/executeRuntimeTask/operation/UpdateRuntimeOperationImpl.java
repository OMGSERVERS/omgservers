package com.omgservers.service.server.task.impl.method.executeRuntimeTask.operation;

import com.omgservers.schema.model.runtimeChangeOfState.RuntimeChangeOfStateDto;
import com.omgservers.schema.shard.runtime.runtimeState.UpdateRuntimeStateRequest;
import com.omgservers.schema.shard.runtime.runtimeState.UpdateRuntimeStateResponse;
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

    @Override
    public Uni<Void> execute(final HandleRuntimeResult handleRuntimeResult) {
        final var runtimeId = handleRuntimeResult.runtimeId();
        final var runtimeChangeOfState = handleRuntimeResult.runtimeChangeOfState();

        return updateDeploymentState(runtimeId, runtimeChangeOfState)
                .replaceWithVoid();
    }

    Uni<Boolean> updateDeploymentState(final Long runtimeId,
                                       final RuntimeChangeOfStateDto runtimeChangeOfState) {
        final var request = new UpdateRuntimeStateRequest(runtimeId, runtimeChangeOfState);
        return runtimeShard.getService().execute(request)
                .map(UpdateRuntimeStateResponse::getUpdated);
    }
}
