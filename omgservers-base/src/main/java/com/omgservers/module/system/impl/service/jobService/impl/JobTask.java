package com.omgservers.module.system.impl.service.jobService.impl;

import com.omgservers.model.job.JobTypeEnum;
import io.smallrye.mutiny.Uni;

public interface JobTask {
    JobTypeEnum getJobType();

    Uni<Boolean> executeTask(Long shardKey, Long entity);
}
