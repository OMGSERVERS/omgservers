package com.omgservers.service.handler.impl.internal;

import com.omgservers.schema.model.clientMessage.ClientMessageModel;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.message.MessageQualifierEnum;
import com.omgservers.schema.model.message.body.DisconnectionReasonEnum;
import com.omgservers.schema.model.message.body.DisconnectionReasonMessageBodyModel;
import com.omgservers.schema.model.tenantMatchmakerRef.TenantMatchmakerRefModel;
import com.omgservers.schema.module.client.DeleteClientRequest;
import com.omgservers.schema.module.client.DeleteClientResponse;
import com.omgservers.schema.module.client.SyncClientMessageRequest;
import com.omgservers.schema.module.client.SyncClientMessageResponse;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerAssignmentRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerAssignmentResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.ViewTenantMatchmakerRefsRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.ViewTenantMatchmakerRefsResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.internal.MatchmakerAssignmentRequestedEventBodyModel;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.client.ClientMessageModelFactory;
import com.omgservers.service.factory.matchmaker.MatchmakerAssignmentModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.client.ClientModule;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchmakerAssignmentRequestedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final ClientModule clientModule;
    final TenantModule tenantModule;

    final MatchmakerAssignmentModelFactory matchmakerAssignmentModelFactory;
    final ClientMessageModelFactory clientMessageModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCHMAKER_ASSIGNMENT_REQUESTED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (MatchmakerAssignmentRequestedEventBodyModel) event.getBody();
        final var clientId = body.getClientId();
        final var tenantId = body.getTenantId();
        final var deploymentId = body.getDeploymentId();

        final var idempotencyKey = event.getId().toString();

        return selectTenantMatchmakerRef(tenantId, deploymentId)
                .flatMap(tenantMatchmakerRef -> {
                    final var matchmakerId = tenantMatchmakerRef.getMatchmakerId();
                    return syncMatchmakerAssignment(matchmakerId, clientId, idempotencyKey)
                            .replaceWithVoid();
                })
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithUni(t -> {
                    log.warn("Matchmaker assignment failed, clientId={}, {}:{}",
                            clientId,
                            t.getClass().getSimpleName(),
                            t.getMessage());
                    return syncDisconnectionMessage(clientId, idempotencyKey)
                            .flatMap(created -> deleteClient(clientId))
                            .replaceWithVoid();
                });
    }

    Uni<TenantMatchmakerRefModel> selectTenantMatchmakerRef(final Long tenantId, final Long deploymentId) {
        return viewTenantMatchmakerRefs(tenantId, deploymentId)
                .map(refs -> {
                    if (refs.isEmpty()) {
                        throw new ServerSideNotFoundException(
                                ExceptionQualifierEnum.MATCHMAKER_NOT_FOUND,
                                String.format("matchmaker was not selected, tenantDeployment=%d/%d",
                                        tenantId,
                                        deploymentId));
                    } else {
                        final var randomRefIndex = ThreadLocalRandom.current().nextInt(refs.size()) % refs.size();
                        final var randomMatchmakerRef = refs.get(randomRefIndex);
                        return randomMatchmakerRef;
                    }
                });
    }

    Uni<List<TenantMatchmakerRefModel>> viewTenantMatchmakerRefs(final Long tenantId, final Long deploymentId) {
        final var request = new ViewTenantMatchmakerRefsRequest(tenantId, deploymentId);
        return tenantModule.getService().viewTenantMatchmakerRefs(request)
                .map(ViewTenantMatchmakerRefsResponse::getTenantMatchmakerRefs);
    }

    Uni<Boolean> syncMatchmakerAssignment(final Long matchmakerId,
                                          final Long clientId,
                                          final String idempotencyKey) {
        final var matchmakerAssignment = matchmakerAssignmentModelFactory.create(matchmakerId,
                clientId,
                idempotencyKey);
        final var request = new SyncMatchmakerAssignmentRequest(matchmakerAssignment);
        return matchmakerModule.getService().syncMatchmakerAssignment(request)
                .map(SyncMatchmakerAssignmentResponse::getCreated)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.warn("Idempotency was violated, object={}, {}", matchmakerAssignment, t.getMessage());
                            return Uni.createFrom().item(Boolean.FALSE);
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    Uni<Boolean> syncDisconnectionMessage(final Long clientId, final String idempotencyKey) {
        final var messageBody = new DisconnectionReasonMessageBodyModel(DisconnectionReasonEnum.INTERNAL_FAILURE);
        final var disconnectionMessage = clientMessageModelFactory.create(clientId,
                MessageQualifierEnum.DISCONNECTION_REASON_MESSAGE,
                messageBody,
                idempotencyKey);
        return syncClientMessage(disconnectionMessage);
    }

    Uni<Boolean> syncClientMessage(final ClientMessageModel clientMessage) {
        final var request = new SyncClientMessageRequest(clientMessage);
        return clientModule.getService().syncClientMessageWithIdempotency(request)
                .map(SyncClientMessageResponse::getCreated);
    }

    Uni<Boolean> deleteClient(final Long clientId) {
        final var request = new DeleteClientRequest(clientId);
        return clientModule.getService().deleteClient(request)
                .map(DeleteClientResponse::getDeleted);
    }
}
