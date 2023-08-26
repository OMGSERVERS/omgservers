package com.omgservers.base.module.internal.impl.service.jobRoutedService.impl;

import com.omgservers.model.job.JobType;
import io.smallrye.mutiny.Uni;

public interface JobTask {
    JobType getJobType();

    Uni<Boolean> executeTask(Long shardKey, Long entity);
}
