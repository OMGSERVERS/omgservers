package com.omgservers.module.system.impl.operation.getJobInterval;

import com.omgservers.model.job.JobType;

public interface GetJobIntervalOperation {
    Integer getJobIntervalInSeconds(JobType type);
}
