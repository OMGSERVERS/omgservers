package com.omgservers.application.module.internalModule.model.job;

import com.omgservers.application.operation.generateIdOperation.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class JobModelFactory {

    final GenerateIdOperation generateIdOperation;

    public JobModel create(final Long shardKey,
                           final Long entity,
                           final JobType type) {
        final var id = generateIdOperation.generateId();
        return create(id, shardKey, entity, type);
    }

    public JobModel create(final Long id,
                           final Long shardKey,
                           final Long entity,
                           final JobType type) {
        Instant now = Instant.now();

        JobModel job = new JobModel();
        job.setId(id);
        job.setCreated(now);
        job.setShardKey(shardKey);
        job.setEntity(entity);
        job.setType(type);
        return job;
    }
}
