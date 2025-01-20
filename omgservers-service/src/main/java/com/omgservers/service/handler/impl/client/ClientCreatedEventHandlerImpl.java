package com.omgservers.service.handler.impl.client;

import com.omgservers.schema.model.client.ClientModel;
import com.omgservers.schema.model.message.MessageQualifierEnum;
import com.omgservers.schema.model.message.body.ServerWelcomeMessageBodyDto;
import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.module.client.GetClientRequest;
import com.omgservers.schema.module.client.GetClientResponse;
import com.omgservers.schema.module.client.SyncClientMessageRequest;
import com.omgservers.schema.module.client.SyncClientMessageResponse;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentResponse;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.client.ClientCreatedEventBodyModel;
import com.omgservers.service.factory.client.ClientMessageModelFactory;
import com.omgservers.service.factory.queue.QueueRequestModelFactory;
import com.omgservers.service.factory.runtime.RuntimeAssignmentModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.shard.client.ClientShard;
import com.omgservers.service.shard.tenant.TenantShard;
import com.omgservers.service.operation.assignment.AssignMatchmakerOperation;
import com.omgservers.service.operation.assignment.SelectRandomMatchmakerOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ClientCreatedEventHandlerImpl implements EventHandler {

    final ClientShard clientShard;
    final TenantShard tenantShard;

    final SelectRandomMatchmakerOperation selectRandomMatchmakerOperation;
    final AssignMatchmakerOperation assignMatchmakerOperation;

    final RuntimeAssignmentModelFactory runtimeAssignmentModelFactory;
    final ClientMessageModelFactory clientMessageModelFactory;
    final QueueRequestModelFactory queueRequestModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.CLIENT_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("{}", event);

        final var body = (ClientCreatedEventBodyModel) event.getBody();
        final var clientId = body.getId();

        final var idempotencyKey = event.getId().toString();

        return getClient(clientId)
                .flatMap(client -> {
                    log.debug("Created, {}", client);

                    final var tenantId = client.getTenantId();
                    final var deploymentId = client.getDeploymentId();

                    return getTenantDeployment(tenantId, deploymentId)
                            .flatMap(tenantDeployment -> {
                                final var deploymentVersionId = tenantDeployment.getVersionId();
                                return getTenantVersion(tenantId, deploymentVersionId)
                                        .flatMap(tenantVersion -> handleEvent(client,
                                                tenantVersion,
                                                idempotencyKey));
                            });
                })
                .replaceWithVoid();
    }

    Uni<ClientModel> getClient(final Long clientId) {
        final var request = new GetClientRequest(clientId);
        return clientShard.getService().getClient(request)
                .map(GetClientResponse::getClient);
    }

    Uni<TenantDeploymentModel> getTenantDeployment(final Long tenantId, final Long id) {
        final var request = new GetTenantDeploymentRequest(tenantId, id);
        return tenantShard.getService().getTenantDeployment(request)
                .map(GetTenantDeploymentResponse::getTenantDeployment);
    }

    Uni<TenantVersionModel> getTenantVersion(final Long tenantId, final Long tenantVersionId) {
        final var request = new GetTenantVersionRequest(tenantId, tenantVersionId);
        return tenantShard.getService().getTenantVersion(request)
                .map(GetTenantVersionResponse::getTenantVersion);
    }

    Uni<Void> handleEvent(final ClientModel client,
                          final TenantVersionModel tenantVersion,
                          final String idempotencyKey) {
        final var clientId = client.getId();
        final var tenantId = client.getTenantId();
        final var tenantDeploymentId = client.getDeploymentId();

        return createWelcomeMessage(client, tenantVersion, idempotencyKey)
                .flatMap(created -> selectRandomMatchmakerOperation.execute(tenantId, tenantDeploymentId)
                        .flatMap(randomSelectedMatchmaker -> assignMatchmakerOperation.execute(clientId,
                                randomSelectedMatchmaker.getId(),
                                idempotencyKey)))
                .replaceWithVoid();
    }

    Uni<Boolean> createWelcomeMessage(final ClientModel client,
                                      final TenantVersionModel tenantVersion,
                                      final String idempotencyKey) {
        final var clientId = client.getId();
        final var tenantId = client.getTenantId();
        final var tenantVersionId = tenantVersion.getId();
        final var tenantVersionCreated = tenantVersion.getCreated();
        final var messageBody = new ServerWelcomeMessageBodyDto(tenantId, tenantVersionId, tenantVersionCreated);
        final var clientMessage = clientMessageModelFactory.create(clientId,
                MessageQualifierEnum.SERVER_WELCOME_MESSAGE,
                messageBody,
                idempotencyKey);

        final var request = new SyncClientMessageRequest(clientMessage);
        return clientShard.getService().syncClientMessageWithIdempotency(request)
                .map(SyncClientMessageResponse::getCreated);
    }
}

