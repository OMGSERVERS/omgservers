package com.omgservers.service.server.task.impl.method.executeRuntimeTask.operation.handleRuntimeCommands.runtimeCommandHandler;

import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import com.omgservers.schema.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.schema.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.schema.model.runtimeCommand.body.RemoveClientRuntimeCommandBodyDto;
import com.omgservers.service.factory.runtime.RuntimeAssignmentModelFactory;
import com.omgservers.service.factory.runtime.RuntimeMessageModelFactory;
import com.omgservers.service.server.task.impl.method.executeRuntimeTask.dto.FetchRuntimeResult;
import com.omgservers.service.server.task.impl.method.executeRuntimeTask.dto.HandleRuntimeResult;
import com.omgservers.service.server.task.impl.method.executeRuntimeTask.operation.handleRuntimeCommands.RuntimeCommandHandler;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class RemoveClientRuntimeCommandHandlerImpl implements RuntimeCommandHandler {

    final RuntimeAssignmentModelFactory runtimeAssignmentModelFactory;
    final RuntimeMessageModelFactory runtimeMessageModelFactory;

    @Override
    public RuntimeCommandQualifierEnum getQualifier() {
        return RuntimeCommandQualifierEnum.REMOVE_CLIENT;
    }

    @Override
    public boolean handle(final FetchRuntimeResult fetchRuntimeResult,
                          final HandleRuntimeResult handleRuntimeResult,
                          final RuntimeCommandModel runtimeCommand) {
        log.debug("Handle command, {}", runtimeCommand);

        final var body = (RemoveClientRuntimeCommandBodyDto) runtimeCommand.getBody();
        final var clientId = body.getClientId();

        final var runtimeAssignmentsToDelete = fetchRuntimeResult.runtimeState().getRuntimeAssignments().stream()
                .filter(runtimeAssignment -> runtimeAssignment.getClientId().equals(clientId))
                .map(RuntimeAssignmentModel::getId)
                .toList();

        handleRuntimeResult.runtimeChangeOfState().getRuntimeAssignmentToDelete()
                .addAll(runtimeAssignmentsToDelete);

        return true;
    }
}
