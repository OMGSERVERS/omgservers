package com.omgservers.service.operation.job;

import com.omgservers.schema.model.job.JobQualifierEnum;
import com.omgservers.service.factory.system.JobModelFactory;
import com.omgservers.service.service.job.JobService;
import com.omgservers.service.service.job.dto.SyncJobRequest;
import com.omgservers.service.service.job.dto.SyncJobResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateJobOperationImpl implements CreateJobOperation {

    final JobService jobService;

    final JobModelFactory jobModelFactory;

    @Override
    public Uni<Boolean> execute(final JobQualifierEnum qualifier,
                                final Long shardKey,
                                final Long entityId,
                                final String idempotencyKey) {
        final var job = jobModelFactory.create(qualifier,
                shardKey,
                entityId,
                idempotencyKey);

        final var syncEventRequest = new SyncJobRequest(job);
        return jobService.syncJobWithIdempotency(syncEventRequest)
                .map(SyncJobResponse::getCreated);
    }

    @Override
    public Uni<Boolean> execute(final JobQualifierEnum qualifier,
                                final Long entityId,
                                final String idempotencyKey) {
        return execute(qualifier, entityId, entityId, idempotencyKey);
    }
}
