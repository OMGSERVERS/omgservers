package com.omgservers.service.service.task.impl.method.executeRuntimeTask.operation;

import com.omgservers.service.operation.server.GetServiceConfigOperation;
import com.omgservers.service.service.task.impl.method.executeRuntimeTask.dto.FetchRuntimeResult;
import com.omgservers.service.service.task.impl.method.executeRuntimeTask.dto.HandleRuntimeResult;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandleClientsLastActivitiesOperationImpl implements HandleClientsLastActivitiesOperation {

    final GetServiceConfigOperation getServiceConfigOperation;

    @Override
    public void execute(final FetchRuntimeResult fetchRuntimeResult,
                        final HandleRuntimeResult handleRuntimeResult) {

        final var lastActivityByClientId = fetchRuntimeResult.lastActivityByClientId();
        final var maxInactiveInterval = getServiceConfigOperation.getServiceConfig().clients()
                .inactiveInterval();

        fetchRuntimeResult.runtimeState().getRuntimeAssignments()
                .forEach(runtimeAssignment -> {
                    final var clientId = runtimeAssignment.getClientId();
                    final var lastActivity = lastActivityByClientId.get(clientId);

                    if (Objects.nonNull(lastActivity)) {
                        final var currentInactiveInterval = Duration.between(lastActivity, Instant.now());

                        if (currentInactiveInterval.toSeconds() >= maxInactiveInterval) {
                            log.info("Client \"{}\" becomes inactive", clientId);

                            handleRuntimeResult.clientsToDelete().add(clientId);
                        }
                    } else {
                        log.info("No last activity for client \"{}\", skip checking", clientId);
                    }
                });
    }
}
