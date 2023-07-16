package com.omgservers.application.module.internalModule.model.job;

import com.omgservers.application.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobModel {

    static public JobModel create(UUID shardKey, UUID entity, JobType type) {
        Instant now = Instant.now();

        JobModel job = new JobModel();
        job.setCreated(now);
        job.setShardKey(shardKey);
        job.setEntity(entity);
        job.setType(type);
        return job;
    }

    static public void validateJobModel(JobModel job) {
        if (job == null) {
            throw new ServerSideBadRequestException("job is null");
        }
    }

    Instant created;
    UUID shardKey;
    UUID entity;
    JobType type;
}
