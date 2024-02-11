package com.omgservers.service.job.runtime;

import com.omgservers.model.dto.system.SyncEventRequest;
import com.omgservers.model.dto.system.SyncEventResponse;
import com.omgservers.model.event.body.InactiveClientDetectedEventBodyModel;
import com.omgservers.model.job.JobQualifierEnum;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.model.runtimeClient.RuntimeClientModel;
import com.omgservers.service.factory.EventModelFactory;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.module.system.impl.service.jobService.impl.JobTask;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RuntimeJobTask implements JobTask {

    final RuntimeModule runtimeModule;
    final SystemModule systemModule;

    final GetConfigOperation getConfigOperation;

    final EventModelFactory eventModelFactory;

    @Override
    public JobQualifierEnum getJobQualifier() {
        return JobQualifierEnum.RUNTIME;
    }

    @Override
    public Uni<Void> executeTask(final Long shardKey, final Long entityId) {
        final var runtimeId = entityId;

        return runtimeModule.getShortcutService().getRuntime(runtimeId)
                .map(runtime -> {
                    if (runtime.getDeleted()) {
                        log.info("Runtime was deleted, skip job execution, runtimeId={}", runtimeId);
                        return null;
                    } else {
                        return runtime;
                    }
                })
                .onItem().ifNotNull().transformToUni(this::handleRuntime)
                .replaceWithVoid();
    }

    Uni<Void> handleRuntime(final RuntimeModel runtime) {
        final var runtimeId = runtime.getId();

        return runtimeModule.getShortcutService().viewRuntimeClients(runtimeId)
                .map(this::filterInactiveClients)
                .flatMap(inactiveClients -> Multi.createFrom().iterable(inactiveClients)
                        .onItem().transformToUniAndConcatenate(this::syncInactiveClientDetectedEvent)
                        .collect().asList()
                        .replaceWithVoid());
    }

    List<RuntimeClientModel> filterInactiveClients(List<RuntimeClientModel> runtimeClients) {
        final var inactiveInterval = getConfigOperation.getServiceConfig().inactiveInterval();
        final var now = Instant.now();

        return runtimeClients.stream()
                .filter(runtimeClient -> runtimeClient
                        .getLastActivity()
                        .plusSeconds(inactiveInterval)
                        .isBefore(now))
                .toList();
    }

    Uni<Boolean> syncInactiveClientDetectedEvent(RuntimeClientModel inactiveClient) {
        final var clientId = inactiveClient.getClientId();
        final var eventBody = new InactiveClientDetectedEventBodyModel(clientId);
        final var eventModel = eventModelFactory.create(eventBody);

        final var syncEventRequest = new SyncEventRequest(eventModel);
        return systemModule.getEventService().syncEvent(syncEventRequest)
                .map(SyncEventResponse::getCreated);
    }
}
