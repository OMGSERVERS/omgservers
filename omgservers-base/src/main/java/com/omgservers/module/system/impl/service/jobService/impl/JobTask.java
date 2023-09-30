package com.omgservers.module.system.impl.service.jobService.impl;

import com.omgservers.model.job.JobQualifierEnum;
import io.smallrye.mutiny.Uni;

public interface JobTask {
    JobQualifierEnum getJobType();

    Uni<Boolean> executeTask(Long shardKey, Long entityId);
}
