package com.omgservers.service.operation.queue;

import com.omgservers.schema.model.client.ClientModel;
import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.schema.module.client.GetClientRequest;
import com.omgservers.schema.module.client.GetClientResponse;
import com.omgservers.schema.module.queue.queueRequest.SyncQueueRequestRequest;
import com.omgservers.schema.module.queue.queueRequest.SyncQueueRequestResponse;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentResponse;
import com.omgservers.service.factory.queue.QueueRequestModelFactory;
import com.omgservers.service.shard.client.ClientShard;
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
class CreateQueueRequestOperationImpl implements CreateQueueRequestOperation {

    final TenantShard tenantShard;
    final ClientShard clientShard;
    final QueueShard queueShard;

    final QueueRequestModelFactory queueRequestModelFactory;

    @Override
    public Uni<Boolean> execute(final Long clientId,
                                final String idempotencyKey) {
        return getClient(clientId)
                .flatMap(client -> {
                    if (client.getDeleted()) {
                        log.warn("The client \"{}\" was already deleted, skip operation", clientId);
                        return Uni.createFrom().item(Boolean.FALSE);
                    }

                    final var tenantId = client.getTenantId();
                    final var tenantDeploymentId = client.getDeploymentId();
                    return getTenantDeployment(tenantId, tenantDeploymentId)
                            .flatMap(tenantDeployment -> {
                                final var queueId = tenantDeployment.getQueueId();
                                return createQueueRequest(queueId, clientId, idempotencyKey);
                            });
                });

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

    Uni<Boolean> createQueueRequest(final Long queueId,
                                    final Long clientId,
                                    final String idempotencyKey) {
        final var queueRequest = queueRequestModelFactory.create(queueId, clientId, idempotencyKey);
        final var request = new SyncQueueRequestRequest(queueRequest);
        return queueShard.getQueueService().executeWithIdempotency(request)
                .map(SyncQueueRequestResponse::getCreated);
    }
}
