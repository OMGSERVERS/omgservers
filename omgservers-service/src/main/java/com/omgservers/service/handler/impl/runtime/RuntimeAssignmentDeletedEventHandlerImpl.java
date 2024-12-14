package com.omgservers.service.handler.impl.runtime;

import com.omgservers.schema.model.clientRuntimeRef.ClientRuntimeRefModel;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import com.omgservers.schema.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.schema.model.runtimeCommand.body.DeleteClientRuntimeCommandBodyDto;
import com.omgservers.schema.module.client.DeleteClientRuntimeRefRequest;
import com.omgservers.schema.module.client.DeleteClientRuntimeRefResponse;
import com.omgservers.schema.module.client.FindClientRuntimeRefRequest;
import com.omgservers.schema.module.client.FindClientRuntimeRefResponse;
import com.omgservers.schema.module.runtime.GetRuntimeAssignmentRequest;
import com.omgservers.schema.module.runtime.GetRuntimeAssignmentResponse;
import com.omgservers.schema.module.runtime.GetRuntimeRequest;
import com.omgservers.schema.module.runtime.GetRuntimeResponse;
import com.omgservers.schema.module.runtime.SyncRuntimeCommandRequest;
import com.omgservers.schema.module.runtime.SyncRuntimeCommandResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.runtime.RuntimeAssignmentDeletedEventBodyModel;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.runtime.RuntimeCommandModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.client.ClientModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RuntimeAssignmentDeletedEventHandlerImpl implements EventHandler {

    final RuntimeModule runtimeModule;
    final ClientModule clientModule;

    final RuntimeCommandModelFactory runtimeCommandModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.RUNTIME_ASSIGNMENT_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (RuntimeAssignmentDeletedEventBodyModel) event.getBody();
        final var runtimeId = body.getRuntimeId();
        final var id = body.getId();

        return getRuntimeAssignment(runtimeId, id)
                .flatMap(runtimeAssignment -> {
                    log.debug("Deleted, {}", runtimeAssignment);

                    final var clientId = runtimeAssignment.getClientId();

                    return findAndDeleteClientRuntimeRef(clientId, runtimeId)
                            .flatMap(voidItem -> getRuntime(runtimeId)
                                    .flatMap(runtime -> {
                                        if (runtime.getDeleted()) {
                                            log.debug("Runtime was already deleted, runtimeId={}", runtimeId);
                                            return Uni.createFrom().voidItem();
                                        }

                                        return syncDeleteClientRuntimeCommand(runtimeId,
                                                clientId,
                                                event.getIdempotencyKey());
                                    }));
                })
                .replaceWithVoid();
    }

    Uni<RuntimeAssignmentModel> getRuntimeAssignment(final Long runtimeId, final Long id) {
        final var request = new GetRuntimeAssignmentRequest(runtimeId, id);
        return runtimeModule.getService().execute(request)
                .map(GetRuntimeAssignmentResponse::getRuntimeAssignment);
    }

    Uni<RuntimeModel> getRuntime(final Long runtimeId) {
        final var request = new GetRuntimeRequest(runtimeId);
        return runtimeModule.getService().execute(request)
                .map(GetRuntimeResponse::getRuntime);
    }

    Uni<Boolean> syncDeleteClientRuntimeCommand(final Long runtimeId,
                                                final Long clientId,
                                                final String idempotencyKey) {
        final var runtimeCommandBody = new DeleteClientRuntimeCommandBodyDto(clientId);
        final var runtimeCommand = runtimeCommandModelFactory.create(runtimeId, runtimeCommandBody, idempotencyKey);
        return syncRuntimeCommand(runtimeCommand);
    }

    Uni<Boolean> syncRuntimeCommand(final RuntimeCommandModel runtimeCommand) {
        final var request = new SyncRuntimeCommandRequest(runtimeCommand);
        return runtimeModule.getService().execute(request)
                .map(SyncRuntimeCommandResponse::getCreated)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}", runtimeCommand, t.getMessage());
                            return Uni.createFrom().item(Boolean.FALSE);
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    Uni<Void> findAndDeleteClientRuntimeRef(final Long clientId, final Long runtimeId) {
        return findClientRuntimeRef(clientId, runtimeId)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithNull()
                .onItem().ifNotNull().transformToUni(clientRuntimeRef ->
                        deleteClientRuntimeRef(clientId, clientRuntimeRef.getId()))
                .replaceWithVoid();
    }

    Uni<ClientRuntimeRefModel> findClientRuntimeRef(final Long clientId, final Long runtimeId) {
        final var request = new FindClientRuntimeRefRequest(clientId, runtimeId);
        return clientModule.getService().findClientRuntimeRef(request)
                .map(FindClientRuntimeRefResponse::getClientRuntimeRef);
    }

    Uni<Boolean> deleteClientRuntimeRef(final Long clientId, final Long id) {
        final var request = new DeleteClientRuntimeRefRequest(clientId, id);
        return clientModule.getService().deleteClientRuntimeRef(request)
                .map(DeleteClientRuntimeRefResponse::getDeleted);
    }
}
