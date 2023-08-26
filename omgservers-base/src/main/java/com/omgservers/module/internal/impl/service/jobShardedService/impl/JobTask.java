package com.omgservers.module.internal.impl.service.jobShardedService.impl;

import com.omgservers.model.job.JobType;
import io.smallrye.mutiny.Uni;

public interface JobTask {
    JobType getJobType();

    Uni<Boolean> executeTask(Long shardKey, Long entity);
}
