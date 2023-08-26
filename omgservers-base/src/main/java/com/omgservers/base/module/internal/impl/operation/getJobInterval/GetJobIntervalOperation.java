package com.omgservers.base.module.internal.impl.operation.getJobInterval;

import com.omgservers.model.job.JobType;

public interface GetJobIntervalOperation {
    Integer getJobIntervalInSeconds(JobType type);
}
