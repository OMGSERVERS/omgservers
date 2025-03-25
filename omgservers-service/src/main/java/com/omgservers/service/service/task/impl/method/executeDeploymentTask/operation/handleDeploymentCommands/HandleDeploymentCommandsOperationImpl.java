package com.omgservers.service.service.task.impl.method.executeDeploymentTask.operation.handleDeploymentCommands;

import com.omgservers.schema.model.deploymentCommand.DeploymentCommandQualifierEnum;
import com.omgservers.service.service.task.impl.method.executeDeploymentTask.dto.FetchDeploymentResult;
import com.omgservers.service.service.task.impl.method.executeDeploymentTask.dto.HandleDeploymentResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
class HandleDeploymentCommandsOperationImpl implements HandleDeploymentCommandsOperation {

    final Map<DeploymentCommandQualifierEnum, DeploymentCommandHandler> deploymentCommandHandlers;

    HandleDeploymentCommandsOperationImpl(final Instance<DeploymentCommandHandler> deploymentCommandHandlerBeans) {
        this.deploymentCommandHandlers = new ConcurrentHashMap<>();
        deploymentCommandHandlerBeans.stream().forEach(deploymentCommandHandler -> {
            final var qualifier = deploymentCommandHandler.getQualifier();
            if (deploymentCommandHandlers.put(qualifier, deploymentCommandHandler) != null) {
                log.error("Multiple \"{}\" handlers were detected", qualifier);
            }
        });
    }

    @Override
    public void execute(final FetchDeploymentResult fetchDeploymentResult,
                        final HandleDeploymentResult handleDeploymentResult) {
        fetchDeploymentResult.deploymentState().getDeploymentCommands()
                .forEach(deploymentCommand -> {
                    final var qualifier = deploymentCommand.getQualifier();
                    final var qualifierBodyClass = qualifier.getBodyClass();
                    final var body = deploymentCommand.getBody();
                    final var commandId = deploymentCommand.getId();

                    if (!qualifierBodyClass.isInstance(body)) {
                        log.error("Qualifier \"{}\" and body class \"{}\" do not match, id={}",
                                qualifier, body.getClass().getSimpleName(), commandId);
                        return;
                    }

                    if (!deploymentCommandHandlers.containsKey(qualifier)) {
                        log.error("Handler for \"{}\" was not found, id={}",
                                qualifier, commandId);
                        return;
                    }

                    final var handled = deploymentCommandHandlers.get(qualifier)
                            .handle(fetchDeploymentResult, handleDeploymentResult, deploymentCommand);

                    if (handled) {
                        handleDeploymentResult.deploymentChangeOfState().getDeploymentCommandsToDelete()
                                .add(deploymentCommand.getId());
                    }
                });
    }
}
