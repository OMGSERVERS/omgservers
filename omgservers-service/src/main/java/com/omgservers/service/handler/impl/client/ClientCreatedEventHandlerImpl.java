package com.omgservers.service.handler.impl.client;

import com.omgservers.schema.message.body.ClientGreetedMessageBodyDto;
import com.omgservers.schema.model.client.ClientModel;
import com.omgservers.schema.model.deployment.DeploymentModel;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.shard.client.client.GetClientRequest;
import com.omgservers.schema.shard.client.client.GetClientResponse;
import com.omgservers.schema.shard.client.clientMessage.SyncClientMessageRequest;
import com.omgservers.schema.shard.client.clientMessage.SyncClientMessageResponse;
import com.omgservers.schema.shard.deployment.deployment.GetDeploymentRequest;
import com.omgservers.schema.shard.deployment.deployment.GetDeploymentResponse;
import com.omgservers.schema.shard.deployment.deploymentRequest.SyncDeploymentRequestRequest;
import com.omgservers.schema.shard.deployment.deploymentRequest.SyncDeploymentRequestResponse;
import com.omgservers.schema.shard.tenant.tenantVersion.GetTenantVersionRequest;
import com.omgservers.schema.shard.tenant.tenantVersion.GetTenantVersionResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.client.ClientCreatedEventBodyModel;
import com.omgservers.service.factory.client.ClientMessageModelFactory;
import com.omgservers.service.factory.deployment.DeploymentRequestModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.shard.client.ClientShard;
import com.omgservers.service.shard.deployment.DeploymentShard;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ClientCreatedEventHandlerImpl implements EventHandler {

    final DeploymentShard deploymentShard;
    final ClientShard clientShard;
    final TenantShard tenantShard;

    final DeploymentRequestModelFactory deploymentRequestModelFactory;
    final ClientMessageModelFactory clientMessageModelFactory;

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

                    final var deploymentId = client.getDeploymentId();

                    return getDeployment(deploymentId)
                            .flatMap(deployment -> {
                                final var tenantId = deployment.getTenantId();
                                final var tenantVersionId = deployment.getVersionId();
                                return getTenantVersion(tenantId, tenantVersionId)
                                        .flatMap(tenantVersion -> createClientGreetedMessage(client,
                                                tenantVersion,
                                                idempotencyKey)
                                                .flatMap(created -> createDeploymentRequest(deploymentId,
                                                        clientId,
                                                        idempotencyKey)));
                            });
                })
                .replaceWithVoid();
    }

    Uni<ClientModel> getClient(final Long clientId) {
        final var request = new GetClientRequest(clientId);
        return clientShard.getService().execute(request)
                .map(GetClientResponse::getClient);
    }

    Uni<DeploymentModel> getDeployment(final Long deploymentId) {
        final var request = new GetDeploymentRequest(deploymentId);
        return deploymentShard.getService().execute(request)
                .map(GetDeploymentResponse::getDeployment);
    }

    Uni<TenantVersionModel> getTenantVersion(final Long tenantId, final Long tenantVersionId) {
        final var request = new GetTenantVersionRequest(tenantId, tenantVersionId);
        return tenantShard.getService().execute(request)
                .map(GetTenantVersionResponse::getTenantVersion);
    }

    Uni<Boolean> createClientGreetedMessage(final ClientModel client,
                                            final TenantVersionModel tenantVersion,
                                            final String idempotencyKey) {
        final var clientId = client.getId();

        final var tenantId = tenantVersion.getTenantId();
        final var tenantVersionId = tenantVersion.getId();
        final var tenantVersionCreated = tenantVersion.getCreated();

        final var messageBody = new ClientGreetedMessageBodyDto(tenantId, tenantVersionId, tenantVersionCreated);
        final var clientMessage = clientMessageModelFactory.create(clientId,
                messageBody,
                idempotencyKey);

        final var request = new SyncClientMessageRequest(clientMessage);
        return clientShard.getService().executeWithIdempotency(request)
                .map(SyncClientMessageResponse::getCreated);
    }

    Uni<Boolean> createDeploymentRequest(final Long deploymentId,
                                         final Long clientId,
                                         final String idempotencyKey) {
        final var deploymentRequest = deploymentRequestModelFactory.create(deploymentId, clientId, idempotencyKey);

        final var request = new SyncDeploymentRequestRequest(deploymentRequest);
        return deploymentShard.getService().executeWithIdempotency(request)
                .map(SyncDeploymentRequestResponse::getCreated);
    }
}

