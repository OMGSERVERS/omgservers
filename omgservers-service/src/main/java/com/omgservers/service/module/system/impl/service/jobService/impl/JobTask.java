package com.omgservers.service.module.system.impl.service.jobService.impl;

import com.omgservers.model.job.JobQualifierEnum;
import io.smallrye.mutiny.Uni;

public interface JobTask {
    JobQualifierEnum getJobQualifier();

    /**
     * Execute task for specified entity.
     *
     * @param shardKey shard key
     * @param entityId id of entity
     */
    Uni<Void> executeTask(Long shardKey, Long entityId);
}
