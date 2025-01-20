package com.omgservers.service.operation.queue;

import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.schema.module.queue.queueRequest.SyncQueueRequestRequest;
import com.omgservers.schema.module.queue.queueRequest.SyncQueueRequestResponse;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentResponse;
import com.omgservers.service.factory.queue.QueueRequestModelFactory;
import com.omgservers.service.module.queue.QueueModule;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateQueueRequestOperationImpl implements CreateQueueRequestOperation {

    final TenantModule tenantModule;
    final QueueModule queueModule;

    final QueueRequestModelFactory queueRequestModelFactory;

    @Override
    public Uni<Boolean> execute(final Long clientId,
                                final Long tenantId,
                                final Long tenantDeploymentId,
                                final String idempotencyKey) {
        return getTenantDeployment(tenantId, tenantDeploymentId)
                .flatMap(tenantDeployment -> {
                    final var queueId = tenantDeployment.getQueueId();
                    return createQueueRequest(queueId, clientId, idempotencyKey);
                });
    }

    Uni<TenantDeploymentModel> getTenantDeployment(final Long tenantId, final Long id) {
        final var request = new GetTenantDeploymentRequest(tenantId, id);
        return tenantModule.getService().getTenantDeployment(request)
                .map(GetTenantDeploymentResponse::getTenantDeployment);
    }

    Uni<Boolean> createQueueRequest(final Long queueId,
                                    final Long clientId,
                                    final String idempotencyKey) {
        final var queueRequest = queueRequestModelFactory.create(queueId, clientId, idempotencyKey);
        final var request = new SyncQueueRequestRequest(queueRequest);
        return queueModule.getQueueService().executeWithIdempotency(request)
                .map(SyncQueueRequestResponse::getCreated);
    }
}
