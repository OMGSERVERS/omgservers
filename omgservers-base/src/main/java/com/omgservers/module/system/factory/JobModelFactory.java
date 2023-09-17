package com.omgservers.module.system.factory;

import com.omgservers.operation.generateId.GenerateIdOperation;
import com.omgservers.model.job.JobModel;
import com.omgservers.model.job.JobTypeEnum;
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

    public JobModel create(final Long shardKey,
                           final Long entityId,
                           final JobTypeEnum type) {
        final var id = generateIdOperation.generateId();
        return create(id, shardKey, entityId, type);
    }

    public JobModel create(final Long id,
                           final Long shardKey,
                           final Long entityId,
                           final JobTypeEnum type) {
        Instant now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        JobModel job = new JobModel();
        job.setId(id);
        job.setCreated(now);
        job.setShardKey(shardKey);
        job.setEntityId(entityId);
        job.setType(type);
        return job;
    }
}
