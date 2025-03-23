package com.omgservers.service.handler.impl.tenant;

import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.schema.module.queue.queue.SyncQueueRequest;
import com.omgservers.schema.module.queue.queue.SyncQueueResponse;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentResponse;
import com.omgservers.schema.module.tenant.tenantLobbyResource.SyncTenantLobbyResourceRequest;
import com.omgservers.schema.module.tenant.tenantLobbyResource.SyncTenantLobbyResourceResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerResource.SyncTenantMatchmakerResourceRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerResource.SyncTenantMatchmakerResourceResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantDeploymentCreatedEventBodyModel;
import com.omgservers.service.factory.queue.QueueModelFactory;
import com.omgservers.service.factory.tenant.TenantLobbyResourceModelFactory;
import com.omgservers.service.factory.tenant.TenantMatchmakerResourceModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.shard.queue.QueueShard;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TenantDeploymentCreatedEventHandlerImpl implements EventHandler {

    final TenantShard tenantShard;
    final QueueShard queueShard;

    final TenantMatchmakerResourceModelFactory tenantMatchmakerResourceModelFactory;
    final TenantLobbyResourceModelFactory tenantLobbyResourceModelFactory;
    final QueueModelFactory queueModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_DEPLOYMENT_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (TenantDeploymentCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        final var idempotencyKey = event.getId().toString();

        return getTenantDeployment(tenantId, id)
                .flatMap(tenantDeployment -> {
                    log.debug("Created, {}", tenantDeployment);

                    // TODO: creating lobby/matchmaker resources only if developer requested it
                    return createTenantLobbyResource(tenantId, id, idempotencyKey)
                            .flatMap(created -> createTenantMatchmakerResource(tenantId, id, idempotencyKey))
                            .flatMap(created -> {
                                final var queueId = tenantDeployment.getQueueId();
                                return createQueue(queueId, tenantId, id, idempotencyKey);
                            });
                })
                .replaceWithVoid();
    }

    Uni<TenantDeploymentModel> getTenantDeployment(final Long tenantId, final Long id) {
        final var request = new GetTenantDeploymentRequest(tenantId, id);
        return tenantShard.getService().getTenantDeployment(request)
                .map(GetTenantDeploymentResponse::getTenantDeployment);
    }

    Uni<Boolean> createTenantLobbyResource(final Long tenantId,
                                           final Long tenantDeploymentId,
                                           final String idempotencyKey) {
        final var tenantLobbyResource = tenantLobbyResourceModelFactory.create(tenantId,
                tenantDeploymentId,
                idempotencyKey);
        final var request = new SyncTenantLobbyResourceRequest(tenantLobbyResource);
        return tenantShard.getService().executeWithIdempotency(request)
                .map(SyncTenantLobbyResourceResponse::getCreated);
    }

    Uni<Boolean> createTenantMatchmakerResource(final Long tenantId,
                                                final Long tenantDeploymentId,
                                                final String idempotencyKey) {
        final var tenantMatchmakerResource = tenantMatchmakerResourceModelFactory
                .create(tenantId, tenantDeploymentId, idempotencyKey);
        final var request = new SyncTenantMatchmakerResourceRequest(tenantMatchmakerResource);
        return tenantShard.getService().executeWithIdempotency(request)
                .map(SyncTenantMatchmakerResourceResponse::getCreated);
    }

    Uni<Boolean> createQueue(final Long queueId,
                             final Long tenantId,
                             final Long tenantDeploymentId,
                             final String idempotencyKey) {
        final var queue = queueModelFactory
                .create(queueId, tenantId, tenantDeploymentId, idempotencyKey);
        final var request = new SyncQueueRequest(queue);
        return queueShard.getQueueService().executeWithIdempotency(request)
                .map(SyncQueueResponse::getCreated);
    }
}
