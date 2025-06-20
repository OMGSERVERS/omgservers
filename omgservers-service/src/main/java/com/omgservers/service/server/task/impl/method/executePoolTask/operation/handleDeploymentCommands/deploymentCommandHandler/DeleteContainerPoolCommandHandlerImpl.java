package com.omgservers.service.server.task.impl.method.executePoolTask.operation.handleDeploymentCommands.deploymentCommandHandler;

import com.omgservers.schema.model.poolCommand.PoolCommandModel;
import com.omgservers.schema.model.poolCommand.PoolCommandQualifierEnum;
import com.omgservers.schema.model.poolCommand.body.DeleteContainerPoolCommandBodyDto;
import com.omgservers.schema.model.poolContainer.PoolContainerModel;
import com.omgservers.schema.model.poolRequest.PoolRequestModel;
import com.omgservers.service.server.task.impl.method.executePoolTask.dto.FetchPoolResult;
import com.omgservers.service.server.task.impl.method.executePoolTask.dto.HandlePoolResult;
import com.omgservers.service.server.task.impl.method.executePoolTask.operation.handleDeploymentCommands.PoolCommandHandler;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeleteContainerPoolCommandHandlerImpl implements PoolCommandHandler {

    @Override
    public PoolCommandQualifierEnum getQualifier() {
        return PoolCommandQualifierEnum.DELETE_CONTAINER;
    }

    @Override
    public boolean handle(final FetchPoolResult fetchPoolResult,
                          final HandlePoolResult handlePoolResult,
                          final PoolCommandModel poolCommand) {
        log.debug("Handle command, {}", poolCommand);

        final var body = (DeleteContainerPoolCommandBodyDto) poolCommand.getBody();
        final var runtimeId = body.getRuntimeId();

        final var poolContainersToDelete = fetchPoolResult.poolState().getPoolContainers().stream()
                .filter(poolContainer -> poolContainer.getRuntimeId().equals(runtimeId))
                .map(PoolContainerModel::getId)
                .toList();

        handlePoolResult.poolChangeOfState().getPoolContainersToDelete()
                .addAll(poolContainersToDelete);

        if (poolContainersToDelete.isEmpty()) {
            log.warn("Containers for runtime \"{}\" not found for deletion", runtimeId);
        } else {
            log.info("Containers for runtime \"{}\" queued for deletion", runtimeId);
        }

        final var poolRequestsToDelete = fetchPoolResult.poolState().getPoolRequests().stream()
                .filter(poolRequest -> poolRequest.getRuntimeId().equals(runtimeId))
                .map(PoolRequestModel::getId)
                .toList();

        handlePoolResult.poolChangeOfState().getPoolRequestsToDelete()
                .addAll(poolRequestsToDelete);

        if (!poolRequestsToDelete.isEmpty()) {
            log.info("Pool requests for runtime \"{}\" queued for deletion", runtimeId);
        }

        return true;
    }
}
