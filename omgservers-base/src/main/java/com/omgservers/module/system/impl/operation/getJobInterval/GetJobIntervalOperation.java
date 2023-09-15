package com.omgservers.module.system.impl.operation.getJobInterval;

import com.omgservers.model.job.JobTypeEnum;

public interface GetJobIntervalOperation {
    Integer getJobIntervalInSeconds(JobTypeEnum type);
}
