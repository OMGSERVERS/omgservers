package com.omgservers.base.impl.operation.getJobIntervalOperation;

import com.omgservers.model.job.JobType;

public interface GetJobIntervalOperation {
    Integer getJobIntervalInSeconds(JobType type);
}
