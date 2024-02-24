package com.omgservers.service.handler.job.task.runtime;

import com.omgservers.model.dto.runtime.GetRuntimeRequest;
import com.omgservers.model.dto.runtime.GetRuntimeResponse;
import com.omgservers.model.dto.runtime.ViewRuntimeClientsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimeClientsResponse;
import com.omgservers.model.dto.system.SyncEventRequest;
import com.omgservers.model.dto.system.SyncEventResponse;
import com.omgservers.model.event.body.InactiveClientDetectedEventBodyModel;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.model.runtimeClient.RuntimeClientModel;
import com.omgservers.service.factory.EventModelFactory;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.system.SystemModule;
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
public class RuntimeJobTaskImpl {

    final RuntimeModule runtimeModule;
    final SystemModule systemModule;

    final GetConfigOperation getConfigOperation;

    final EventModelFactory eventModelFactory;

    public Uni<Boolean> executeTask(final Long runtimeId) {
        return getRuntime(runtimeId)
                .flatMap(runtime -> {
                    if (runtime.getDeleted()) {
                        log.info("Runtime was deleted, cancel job execution, runtimeId={}", runtimeId);
                        return Uni.createFrom().item(false);
                    } else {
                        return handleRuntime(runtime)
                                .replaceWith(true);
                    }
                });
    }

    Uni<RuntimeModel> getRuntime(final Long id) {
        final var request = new GetRuntimeRequest(id);
        return runtimeModule.getRuntimeService().getRuntime(request)
                .map(GetRuntimeResponse::getRuntime);
    }

    Uni<Void> handleRuntime(final RuntimeModel runtime) {
        final var runtimeId = runtime.getId();

        return viewRuntimeClients(runtimeId)
                .map(this::filterInactiveClients)
                .flatMap(inactiveClients -> Multi.createFrom().iterable(inactiveClients)
                        .onItem().transformToUniAndConcatenate(this::syncInactiveClientDetectedEvent)
                        .collect().asList())
                .replaceWithVoid();
    }

    Uni<List<RuntimeClientModel>> viewRuntimeClients(final Long runtimeId) {
        final var request = new ViewRuntimeClientsRequest(runtimeId);
        return runtimeModule.getRuntimeService().viewRuntimeClients(request)
                .map(ViewRuntimeClientsResponse::getRuntimeClients);
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