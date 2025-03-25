package com.omgservers.service.service.task.impl.method.executeRuntimeTask.operation.handleRuntimeCommands.runtimeCommandHandler;

import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentConfigDto;
import com.omgservers.schema.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.schema.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.schema.model.runtimeCommand.body.AssignClientRuntimeCommandBodyDto;
import com.omgservers.service.factory.runtime.RuntimeAssignmentModelFactory;
import com.omgservers.service.factory.runtime.RuntimeMessageModelFactory;
import com.omgservers.service.service.task.impl.method.executeRuntimeTask.dto.FetchRuntimeResult;
import com.omgservers.service.service.task.impl.method.executeRuntimeTask.dto.HandleRuntimeResult;
import com.omgservers.service.service.task.impl.method.executeRuntimeTask.operation.handleRuntimeCommands.RuntimeCommandHandler;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AssignClientRuntimeCommandHandlerImpl implements RuntimeCommandHandler {

    final RuntimeAssignmentModelFactory runtimeAssignmentModelFactory;
    final RuntimeMessageModelFactory runtimeMessageModelFactory;

    @Override
    public RuntimeCommandQualifierEnum getQualifier() {
        return RuntimeCommandQualifierEnum.ASSIGN_CLIENT;
    }

    @Override
    public boolean handle(final FetchRuntimeResult fetchRuntimeResult,
                          final HandleRuntimeResult handleRuntimeResult,
                          final RuntimeCommandModel runtimeCommand) {
        log.trace("Handle command, {}", runtimeCommand);

        final var body = (AssignClientRuntimeCommandBodyDto) runtimeCommand.getBody();
        final var clientId = body.getClientId();
        final var groupName = body.getGroupName();

        final var runtimeId = fetchRuntimeResult.runtimeId();
        final var idempotencyKey = runtimeCommand.getId().toString();
        final var config = new RuntimeAssignmentConfigDto();
        config.setGroupName(groupName);
        final var runtimeAssignment = runtimeAssignmentModelFactory.create(runtimeId,
                clientId,
                config,
                idempotencyKey);

        handleRuntimeResult.runtimeChangeOfState().getRuntimeAssignmentToSync()
                .add(runtimeAssignment);
        return true;
    }
}
