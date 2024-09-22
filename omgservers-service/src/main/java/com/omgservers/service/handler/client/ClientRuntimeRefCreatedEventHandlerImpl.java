package com.omgservers.service.handler.client;

import com.omgservers.schema.model.clientRuntimeRef.ClientRuntimeRefModel;
import com.omgservers.schema.model.message.MessageQualifierEnum;
import com.omgservers.schema.model.message.body.RuntimeAssignmentMessageBodyModel;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import com.omgservers.schema.module.client.GetClientRuntimeRefRequest;
import com.omgservers.schema.module.client.GetClientRuntimeRefResponse;
import com.omgservers.schema.module.client.SyncClientMessageRequest;
import com.omgservers.schema.module.client.SyncClientMessageResponse;
import com.omgservers.schema.module.client.ViewClientRuntimeRefsRequest;
import com.omgservers.schema.module.client.ViewClientRuntimeRefsResponse;
import com.omgservers.schema.module.runtime.DeleteRuntimeAssignmentRequest;
import com.omgservers.schema.module.runtime.DeleteRuntimeAssignmentResponse;
import com.omgservers.schema.module.runtime.FindRuntimeAssignmentRequest;
import com.omgservers.schema.module.runtime.FindRuntimeAssignmentResponse;
import com.omgservers.schema.module.runtime.GetRuntimeRequest;
import com.omgservers.schema.module.runtime.GetRuntimeResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.client.ClientRuntimeRefCreatedEventBodyModel;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.client.ClientMessageModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.client.ClientModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ClientRuntimeRefCreatedEventHandlerImpl implements EventHandler {

    final RuntimeModule runtimeModule;
    final ClientModule clientModule;

    final ClientMessageModelFactory clientMessageModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.CLIENT_RUNTIME_REF_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (ClientRuntimeRefCreatedEventBodyModel) event.getBody();
        final var clientId = body.getClientId();
        final var id = body.getId();

        final var idempotencyKey = event.getId().toString();

        return getClientRuntimeRef(clientId, id)
                .flatMap(clientRuntimeRef -> {
                    final var runtimeId = clientRuntimeRef.getRuntimeId();

                    log.info("Client runtime ref was created, clientRuntimeRef={}/{}, runtimeId={}",
                            clientId, id, runtimeId);

                    return getRuntime(runtimeId)
                            .flatMap(runtime -> syncRuntimeAssignmentMessage(runtime, clientId, idempotencyKey)
                                    .flatMap(created -> handlePreviousClientRuntimeRefs(clientRuntimeRef)));
                })
                .replaceWithVoid();
    }

    Uni<ClientRuntimeRefModel> getClientRuntimeRef(final Long clientId, final Long id) {
        final var request = new GetClientRuntimeRefRequest(clientId, id);
        return clientModule.getClientService().getClientRuntimeRef(request)
                .map(GetClientRuntimeRefResponse::getClientRuntimeRef);
    }

    Uni<RuntimeModel> getRuntime(final Long id) {
        final var request = new GetRuntimeRequest(id);
        return runtimeModule.getRuntimeService().getRuntime(request)
                .map(GetRuntimeResponse::getRuntime);
    }

    Uni<Boolean> syncRuntimeAssignmentMessage(final RuntimeModel runtime,
                                              final Long clientId,
                                              final String idempotencyKey) {
        final var messageBody = new RuntimeAssignmentMessageBodyModel(runtime.getId(),
                runtime.getQualifier(),
                runtime.getConfig());
        final var clientMessage = clientMessageModelFactory.create(clientId,
                MessageQualifierEnum.RUNTIME_ASSIGNMENT_MESSAGE,
                messageBody,
                idempotencyKey);
        final var request = new SyncClientMessageRequest(clientMessage);
        return clientModule.getClientService().syncClientMessageWithIdempotency(request)
                .map(SyncClientMessageResponse::getCreated);
    }

    Uni<Void> handlePreviousClientRuntimeRefs(final ClientRuntimeRefModel currentClientRuntimeRef) {
        final var clientId = currentClientRuntimeRef.getClientId();
        final var id = currentClientRuntimeRef.getId();
        return viewClientRuntimeRefs(clientId)
                .flatMap(clientRuntimeRefs -> Multi.createFrom().iterable(clientRuntimeRefs)
                        .onItem().transformToUniAndConcatenate(clientRuntimeRef -> {
                            if (clientRuntimeRef.getId().equals(id)) {
                                return Uni.createFrom().voidItem();
                            } else {
                                final var runtimeId = clientRuntimeRef.getRuntimeId();
                                return findAndDeleteRuntimeAssignment(runtimeId, clientId);
                            }
                        })
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<List<ClientRuntimeRefModel>> viewClientRuntimeRefs(final Long clientId) {
        final var request = new ViewClientRuntimeRefsRequest(clientId);
        return clientModule.getClientService().viewClientRuntimeRefs(request)
                .map(ViewClientRuntimeRefsResponse::getClientRuntimeRefs);
    }

    Uni<Void> findAndDeleteRuntimeAssignment(final Long runtimeId, final Long clientId) {
        return findRuntimeAssignment(runtimeId, clientId)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithNull()
                .onItem().ifNotNull().transformToUni(runtimeAssignment ->
                        deleteRuntimeAssignment(runtimeId, runtimeAssignment.getId()))
                .replaceWithVoid();
    }

    Uni<RuntimeAssignmentModel> findRuntimeAssignment(final Long runtimeId, final Long clientId) {
        final var request = new FindRuntimeAssignmentRequest(runtimeId, clientId);
        return runtimeModule.getRuntimeService().findRuntimeAssignment(request)
                .map(FindRuntimeAssignmentResponse::getRuntimeAssignment);
    }

    Uni<Boolean> deleteRuntimeAssignment(final Long runtimeId, final Long id) {
        final var request = new DeleteRuntimeAssignmentRequest(runtimeId, id);
        return runtimeModule.getRuntimeService().deleteRuntimeAssignment(request)
                .map(DeleteRuntimeAssignmentResponse::getDeleted);
    }
}

