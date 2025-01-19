package com.omgservers.testDataFactory;

import com.omgservers.schema.model.queue.QueueModel;
import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.schema.module.queue.queue.SyncQueueRequest;
import com.omgservers.service.factory.queue.QueueModelFactory;
import com.omgservers.service.module.queue.impl.service.queueService.testInterface.QueueServiceTestInterface;
import com.omgservers.service.operation.server.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class QueueTestDataFactory {

    final QueueServiceTestInterface queueService;

    final GenerateIdOperation generateIdOperation;

    final QueueModelFactory queueModelFactory;

    public QueueModel createQueue(final TenantDeploymentModel tenantDeployment) {

        final var queueId = tenantDeployment.getQueueId();
        final var tenantId = tenantDeployment.getTenantId();
        final var tenantDeploymentId = tenantDeployment.getId();

        final var queue = queueModelFactory.create(queueId, tenantId, tenantDeploymentId);
        final var syncQueueRequest = new SyncQueueRequest(queue);
        queueService.execute(syncQueueRequest);
        return queue;
    }
}
