package com.omgservers.service.handler.client;

import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.internal.LobbyAssignmentRequestedEventBodyModel;
import com.omgservers.service.event.body.internal.MatchmakerAssignmentRequestedEventBodyModel;
import com.omgservers.service.event.body.module.client.ClientCreatedEventBodyModel;
import com.omgservers.schema.model.client.ClientModel;
import com.omgservers.schema.model.message.MessageQualifierEnum;
import com.omgservers.schema.model.message.body.ServerWelcomeMessageBodyModel;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.module.client.GetClientRequest;
import com.omgservers.schema.module.client.GetClientResponse;
import com.omgservers.schema.module.client.SyncClientMessageRequest;
import com.omgservers.schema.module.client.SyncClientMessageResponse;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionResponse;
import com.omgservers.service.service.event.dto.SyncEventRequest;
import com.omgservers.service.service.event.dto.SyncEventResponse;
import com.omgservers.service.factory.client.ClientMessageModelFactory;
import com.omgservers.service.factory.runtime.RuntimeAssignmentModelFactory;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.client.ClientModule;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.service.event.EventService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ClientCreatedEventHandlerImpl implements EventHandler {

    final ClientModule clientModule;
    final TenantModule tenantModule;

    final EventService eventService;

    final ClientMessageModelFactory clientMessageModelFactory;
    final RuntimeAssignmentModelFactory runtimeAssignmentModelFactory;

    final EventModelFactory eventModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.CLIENT_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (ClientCreatedEventBodyModel) event.getBody();
        final var clientId = body.getId();

        return getClient(clientId)
                .flatMap(client -> {
                    final var tenantId = client.getTenantId();
                    final var versionId = client.getDeploymentId();

                    return getVersion(tenantId, versionId)
                            .flatMap(version -> {
                                log.info("Client was created, id={}, version={}/{}",
                                        clientId, tenantId, versionId);

                                final var idempotencyKey = event.getId().toString();
                                return syncWelcomeMessage(client, version, idempotencyKey)
                                        .flatMap(created -> requestLobbyAssignment(client, idempotencyKey))
                                        .flatMap(created -> requestMatchmakerAssignment(client, idempotencyKey));
                            });
                })
                .replaceWithVoid();
    }

    Uni<ClientModel> getClient(final Long clientId) {
        final var request = new GetClientRequest(clientId);
        return clientModule.getClientService().getClient(request)
                .map(GetClientResponse::getClient);
    }

    Uni<TenantVersionModel> getTenantVersion(final Long tenantId, final Long id) {
        final var request = new GetTenantVersionRequest(tenantId, id);
        return tenantModule.getTenantService().getTenantVersion(request)
                .map(GetTenantVersionResponse::getTenantVersion);
    }

    Uni<Boolean> syncWelcomeMessage(final ClientModel client,
                                    final TenantVersionModel version,
                                    final String idempotencyKey) {
        final var clientId = client.getId();
        final var tenantId = client.getTenantId();
        final var versionId = client.getDeploymentId();
        final var created = version.getCreated();
        final var messageBody = new ServerWelcomeMessageBodyModel(tenantId, versionId, created);
        final var clientMessage = clientMessageModelFactory.create(clientId,
                MessageQualifierEnum.SERVER_WELCOME_MESSAGE,
                messageBody,
                idempotencyKey);

        final var request = new SyncClientMessageRequest(clientMessage);
        return clientModule.getClientService().syncClientMessageWithIdempotency(request)
                .map(SyncClientMessageResponse::getCreated);
    }

    Uni<Boolean> requestLobbyAssignment(final ClientModel client,
                                        final String idempotencyKey) {
        final var clientId = client.getId();
        final var tenantId = client.getTenantId();
        final var versionId = client.getDeploymentId();
        final var eventBody = new LobbyAssignmentRequestedEventBodyModel(clientId, tenantId, versionId);
        final var eventModel = eventModelFactory.create(eventBody,
                idempotencyKey + "/" + eventBody.getQualifier());

        final var syncEventRequest = new SyncEventRequest(eventModel);
        return eventService.syncEventWithIdempotency(syncEventRequest)
                .map(SyncEventResponse::getCreated);
    }

    Uni<Boolean> requestMatchmakerAssignment(final ClientModel client,
                                             final String idempotencyKey) {
        final var clientId = client.getId();
        final var tenantId = client.getTenantId();
        final var versionId = client.getDeploymentId();
        final var eventBody = new MatchmakerAssignmentRequestedEventBodyModel(clientId, tenantId, versionId);
        final var eventModel = eventModelFactory.create(eventBody,
                idempotencyKey + "/" + eventBody.getQualifier());

        final var syncEventRequest = new SyncEventRequest(eventModel);
        return eventService.syncEventWithIdempotency(syncEventRequest)
                .map(SyncEventResponse::getCreated);
    }
}

