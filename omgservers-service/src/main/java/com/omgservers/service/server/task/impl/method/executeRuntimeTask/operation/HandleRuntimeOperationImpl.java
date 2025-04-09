package com.omgservers.service.server.task.impl.method.executeRuntimeTask.operation;

import com.omgservers.schema.model.runtimeChangeOfState.RuntimeChangeOfStateDto;
import com.omgservers.service.server.task.impl.method.executeRuntimeTask.dto.FetchRuntimeResult;
import com.omgservers.service.server.task.impl.method.executeRuntimeTask.dto.HandleRuntimeResult;
import com.omgservers.service.server.task.impl.method.executeRuntimeTask.operation.handleRuntimeCommands.HandleRuntimeCommandsOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class HandleRuntimeOperationImpl implements HandleRuntimeOperation {

    final HandleClientsLastActivitiesOperation handleClientsLastActivitiesOperation;
    final HandleRuntimeLastActivityOperation handleRuntimeLastActivityOperation;
    final HandleRuntimeCommandsOperation handleRuntimeCommandsOperation;

    @Override
    public HandleRuntimeResult execute(final FetchRuntimeResult fetchRuntimeResult) {
        final var runtimeId = fetchRuntimeResult.runtimeId();
        final var handleRuntimeResult = new HandleRuntimeResult(runtimeId,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new RuntimeChangeOfStateDto());

        handleRuntimeLastActivityOperation.execute(fetchRuntimeResult, handleRuntimeResult);
        handleClientsLastActivitiesOperation.execute(fetchRuntimeResult, handleRuntimeResult);
        handleRuntimeCommandsOperation.execute(fetchRuntimeResult, handleRuntimeResult);

        return handleRuntimeResult;
    }
}
