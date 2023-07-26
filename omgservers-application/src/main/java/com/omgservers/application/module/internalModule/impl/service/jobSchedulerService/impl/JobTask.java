package com.omgservers.application.module.internalModule.impl.service.jobSchedulerService.impl;

import com.omgservers.application.module.internalModule.model.job.JobType;
import io.smallrye.mutiny.Uni;

import java.util.UUID;

public interface JobTask {
    JobType getJobType();

    Uni<Boolean> executeTask(Long shardKey, Long entity);
}
