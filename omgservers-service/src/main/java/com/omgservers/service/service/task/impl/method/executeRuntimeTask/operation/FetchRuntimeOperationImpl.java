package com.omgservers.service.service.task.impl.method.executeRuntimeTask.operation;

import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import com.omgservers.schema.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.schema.model.runtimeState.RuntimeStateDto;
import com.omgservers.schema.module.runtime.runtimeCommand.ViewRuntimeCommandsRequest;
import com.omgservers.schema.module.runtime.runtimeCommand.ViewRuntimeCommandsResponse;
import com.omgservers.schema.module.runtime.runtimeState.GetRuntimeStateRequest;
import com.omgservers.schema.module.runtime.runtimeState.GetRuntimeStateResponse;
import com.omgservers.service.service.task.impl.method.executeRuntimeTask.dto.FetchRuntimeResult;
import com.omgservers.service.shard.runtime.RuntimeShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class FetchRuntimeOperationImpl implements FetchRuntimeOperation {

    final RuntimeShard runtimeShard;

    final GetClientsLastActivitiesOperation getClientsLastActivitiesOperation;
    final GetRuntimeLastActivityOperation getRuntimeLastActivityOperation;

    @Override
    public Uni<FetchRuntimeResult> execute(final Long runtimeId) {
        return viewRuntimeCommands(runtimeId)
                .flatMap(runtimeCommands -> getRuntimeLastActivityOperation.execute(runtimeId)
                        .flatMap(lastActivity -> getRuntimeState(runtimeId)
                                .flatMap(runtimeState -> {
                                    final var clientIds = runtimeState.getRuntimeAssignments().stream()
                                            .map(RuntimeAssignmentModel::getClientId)
                                            .toList();

                                    return getClientsLastActivitiesOperation.execute(clientIds)
                                            .map(lastActivityByClientId -> new FetchRuntimeResult(runtimeId,
                                                    lastActivity,
                                                    runtimeState,
                                                    lastActivityByClientId));
                                })
                        )
                );
    }

    Uni<List<RuntimeCommandModel>> viewRuntimeCommands(final Long runtimeId) {
        final var request = new ViewRuntimeCommandsRequest(runtimeId);
        return runtimeShard.getService().execute(request)
                .map(ViewRuntimeCommandsResponse::getRuntimeCommands);
    }

    Uni<RuntimeStateDto> getRuntimeState(final Long runtimeId) {
        final var request = new GetRuntimeStateRequest(runtimeId);
        return runtimeShard.getService().execute(request)
                .map(GetRuntimeStateResponse::getRuntimeState);
    }
}
