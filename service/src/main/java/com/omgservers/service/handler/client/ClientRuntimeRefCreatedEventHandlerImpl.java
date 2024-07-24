package com.omgservers.service.handler.client;

import com.omgservers.model.client.ClientModel;
import com.omgservers.model.clientMessage.ClientMessageModel;
import com.omgservers.model.clientRuntimeRef.ClientRuntimeRefModel;
import com.omgservers.model.dto.client.GetClientRequest;
import com.omgservers.model.dto.client.GetClientResponse;
import com.omgservers.model.dto.client.GetClientRuntimeRefRequest;
import com.omgservers.model.dto.client.GetClientRuntimeRefResponse;
import com.omgservers.model.dto.client.SyncClientMessageRequest;
import com.omgservers.model.dto.client.SyncClientMessageResponse;
import com.omgservers.model.dto.client.ViewClientRuntimeRefsRequest;
import com.omgservers.model.dto.client.ViewClientRuntimeRefsResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeAssignmentRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeAssignmentResponse;
import com.omgservers.model.dto.runtime.FindRuntimeAssignmentRequest;
import com.omgservers.model.dto.runtime.FindRuntimeAssignmentResponse;
import com.omgservers.model.dto.runtime.GetRuntimeRequest;
import com.omgservers.model.dto.runtime.GetRuntimeResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.module.client.ClientRuntimeRefCreatedEventBodyModel;
import com.omgservers.model.exception.ExceptionQualifierEnum;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.RuntimeAssignmentMessageBodyModel;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.model.runtimeAssignment.RuntimeAssignmentModel;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.client.ClientMessageModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.client.ClientModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.operation.issueJwtToken.IssueJwtTokenOperation;
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

    final IssueJwtTokenOperation issueJwtTokenOperation;

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

        return getClient(clientId)
                .flatMap(client -> getClientRuntimeRef(clientId, id)
                        .flatMap(clientRuntimeRef -> {
                            final var runtimeId = clientRuntimeRef.getRuntimeId();

                            log.info("Client runtime ref was created, clientRuntimeRef={}/{}, runtimeId={}",
                                    clientId, id, runtimeId);

                            return getRuntime(runtimeId)
                                    .flatMap(runtime -> {
                                        final var idempotencyKey = event.getId().toString();
                                        return syncRuntimeAssignmentMessage(runtime, clientId, idempotencyKey)
                                                .flatMap(created -> handlePreviousClientRuntimeRefs(
                                                        clientRuntimeRef));
                                    });
                        }))
                .replaceWithVoid();
    }

    Uni<ClientModel> getClient(final Long clientId) {
        final var request = new GetClientRequest(clientId);
        return clientModule.getClientService().getClient(request)
                .map(GetClientResponse::getClient);
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
        final var wsToken = issueJwtTokenOperation.issueWsJwtToken(clientId, runtime.getId(), UserRoleEnum.PLAYER);
        final var messageBody = new RuntimeAssignmentMessageBodyModel(runtime.getId(),
                runtime.getQualifier(),
                runtime.getConfig(),
                wsToken);
        final var clientMessage = clientMessageModelFactory.create(clientId,
                MessageQualifierEnum.RUNTIME_ASSIGNMENT_MESSAGE,
                messageBody,
                idempotencyKey);
        return syncClientMessage(clientMessage);
    }

    Uni<Boolean> syncClientMessage(final ClientMessageModel clientMessage) {
        final var request = new SyncClientMessageRequest(clientMessage);
        return clientModule.getClientService().syncClientMessage(request)
                .map(SyncClientMessageResponse::getCreated)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.warn("Idempotency was violated, object={}, {}", clientMessage, t.getMessage());
                            return Uni.createFrom().item(Boolean.FALSE);
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
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

