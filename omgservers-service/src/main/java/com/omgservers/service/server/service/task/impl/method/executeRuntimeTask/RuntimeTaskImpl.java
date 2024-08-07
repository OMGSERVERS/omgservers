package com.omgservers.service.server.service.task.impl.method.executeRuntimeTask;

import com.omgservers.schema.module.runtime.GetRuntimeRequest;
import com.omgservers.schema.module.runtime.GetRuntimeResponse;
import com.omgservers.schema.module.runtime.ViewRuntimeAssignmentsRequest;
import com.omgservers.schema.module.runtime.ViewRuntimeAssignmentsResponse;
import com.omgservers.schema.service.system.SyncEventRequest;
import com.omgservers.schema.service.system.SyncEventResponse;
import com.omgservers.schema.event.body.internal.InactiveClientDetectedEventBodyModel;
import com.omgservers.schema.event.body.internal.InactiveRuntimeDetectedEventBodyModel;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.server.operation.getConfig.GetConfigOperation;
import com.omgservers.service.server.service.event.EventService;
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
public class RuntimeTaskImpl {

    final RuntimeModule runtimeModule;

    final EventService eventService;

    final GetConfigOperation getConfigOperation;

    final EventModelFactory eventModelFactory;

    public Uni<Boolean> executeTask(final Long runtimeId) {
        return getRuntime(runtimeId)
                .flatMap(runtime -> handleRuntime(runtime)
                        .replaceWith(Boolean.TRUE));
    }

    Uni<RuntimeModel> getRuntime(final Long id) {
        final var request = new GetRuntimeRequest(id);
        return runtimeModule.getRuntimeService().getRuntime(request)
                .map(GetRuntimeResponse::getRuntime);
    }

    Uni<Void> handleRuntime(final RuntimeModel runtime) {
        return checkRuntimeInactivity(runtime)
                .flatMap(voidItem -> detectInactiveClients(runtime));
    }

    Uni<Void> checkRuntimeInactivity(final RuntimeModel runtime) {
        final var inactiveInterval = getConfigOperation.getServiceConfig().workers().inactiveInterval();
        final var now = Instant.now();
        if (runtime.getLastActivity().plusSeconds(inactiveInterval).isBefore(now)) {
            return syncInactiveRuntimeDetectedEvent(runtime.getId())
                    .replaceWithVoid();
        } else {
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Boolean> syncInactiveRuntimeDetectedEvent(final Long runtimeId) {
        final var eventBody = new InactiveRuntimeDetectedEventBodyModel(runtimeId);
        final var eventModel = eventModelFactory.create(eventBody);

        final var syncEventRequest = new SyncEventRequest(eventModel);
        return eventService.syncEvent(syncEventRequest)
                .map(SyncEventResponse::getCreated);
    }

    Uni<Void> detectInactiveClients(final RuntimeModel runtime) {
        final var runtimeId = runtime.getId();
        return viewRuntimeAssignments(runtimeId)
                .map(this::filterInactiveClients)
                .flatMap(inactiveClients -> Multi.createFrom().iterable(inactiveClients)
                        .onItem().transformToUniAndConcatenate(this::syncInactiveClientDetectedEvent)
                        .collect().asList())
                .replaceWithVoid();
    }

    Uni<List<RuntimeAssignmentModel>> viewRuntimeAssignments(final Long runtimeId) {
        final var request = new ViewRuntimeAssignmentsRequest(runtimeId);
        return runtimeModule.getRuntimeService().viewRuntimeAssignments(request)
                .map(ViewRuntimeAssignmentsResponse::getRuntimeAssignments);
    }

    List<RuntimeAssignmentModel> filterInactiveClients(List<RuntimeAssignmentModel> runtimeAssignments) {
        final var inactiveInterval = getConfigOperation.getServiceConfig().clients().inactiveInterval();
        final var now = Instant.now();

        return runtimeAssignments.stream()
                .filter(runtimeAssignment -> runtimeAssignment
                        .getLastActivity()
                        .plusSeconds(inactiveInterval)
                        .isBefore(now))
                .toList();
    }

    Uni<Boolean> syncInactiveClientDetectedEvent(RuntimeAssignmentModel inactiveClient) {
        final var clientId = inactiveClient.getClientId();
        final var eventBody = new InactiveClientDetectedEventBodyModel(clientId);
        final var eventModel = eventModelFactory.create(eventBody);

        final var syncEventRequest = new SyncEventRequest(eventModel);
        return eventService.syncEvent(syncEventRequest)
                .map(SyncEventResponse::getCreated);
    }
}
