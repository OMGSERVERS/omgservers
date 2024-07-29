package com.omgservers.service.factory.system;

import com.omgservers.schema.model.job.JobModel;
import com.omgservers.schema.model.job.JobQualifierEnum;
import com.omgservers.service.server.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class JobModelFactory {

    final GenerateIdOperation generateIdOperation;

    public JobModel create(final JobQualifierEnum qualifier,
                           final Long entityId) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, qualifier, entityId, idempotencyKey);
    }

    public JobModel create(final Long id,
                           final JobQualifierEnum qualifier,
                           final Long entityId) {
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, qualifier, entityId, idempotencyKey);
    }

    public JobModel create(final JobQualifierEnum qualifier,
                           final Long entityId,
                           final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, qualifier, entityId, idempotencyKey);
    }

    public JobModel create(final Long id,
                           final JobQualifierEnum qualifier,
                           final Long entityId,
                           final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var job = new JobModel();
        job.setId(id);
        job.setIdempotencyKey(idempotencyKey);
        job.setCreated(now);
        job.setModified(now);
        job.setQualifier(qualifier);
        job.setEntityId(entityId);
        job.setDeleted(false);
        return job;
    }
}
