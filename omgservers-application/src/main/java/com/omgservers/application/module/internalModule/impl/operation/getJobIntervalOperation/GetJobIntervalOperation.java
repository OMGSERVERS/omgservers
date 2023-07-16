package com.omgservers.application.module.internalModule.impl.operation.getJobIntervalOperation;

import com.omgservers.application.module.internalModule.model.job.JobType;

public interface GetJobIntervalOperation {
    Integer getJobIntervalInSeconds(JobType type);
}
