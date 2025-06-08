package com.omgservers.service.server.task.impl.method.executeStageTask.operation.handleTenantStageCommands;

import com.omgservers.schema.model.tenantStageCommand.TenantStageCommandQualifierEnum;
import com.omgservers.service.server.task.impl.method.executeStageTask.dto.FetchTenantStageResult;
import com.omgservers.service.server.task.impl.method.executeStageTask.dto.HandleTenantStageResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
class HandleTenantStageCommandsOperationImpl implements HandleTenantStageCommandsOperation {

    final Map<TenantStageCommandQualifierEnum, TenantStageCommandHandler> tenantStageCommandHandlers;

    HandleTenantStageCommandsOperationImpl(final Instance<TenantStageCommandHandler> tenantStageCommandHandlerBeans) {
        this.tenantStageCommandHandlers = new ConcurrentHashMap<>();
        tenantStageCommandHandlerBeans.stream().forEach(tenantStageCommandHandler -> {
            final var qualifier = tenantStageCommandHandler.getQualifier();
            if (tenantStageCommandHandlers.put(qualifier, tenantStageCommandHandler) != null) {
                log.error("Multiple \"{}\" handlers were detected", qualifier);
            }
        });
    }

    @Override
    public void execute(final FetchTenantStageResult fetchTenantStageResult,
                        final HandleTenantStageResult handleTenantStageResult) {
        fetchTenantStageResult.tenantStageState().getTenantStageCommands()
                .forEach(tenantStageCommand -> {
                    final var qualifier = tenantStageCommand.getQualifier();
                    final var qualifierBodyClass = qualifier.getBodyClass();
                    final var body = tenantStageCommand.getBody();
                    final var commandId = tenantStageCommand.getId();

                    if (!qualifierBodyClass.isInstance(body)) {
                        log.error("Qualifier \"{}\" and body class \"{}\" do not match, id={}",
                                qualifier, body.getClass().getSimpleName(), commandId);
                        return;
                    }

                    if (!tenantStageCommandHandlers.containsKey(qualifier)) {
                        log.error("Handler for \"{}\" not found, id={}",
                                qualifier, commandId);
                        return;
                    }

                    final var handled = tenantStageCommandHandlers.get(qualifier).handle(fetchTenantStageResult,
                            handleTenantStageResult, tenantStageCommand);

                    if (handled) {
                        handleTenantStageResult.tenantStageChangeOfState().getTenantStageCommandsToDelete()
                                .add(tenantStageCommand.getId());
                    }
                });
    }
}