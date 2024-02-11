package com.omgservers.service.module.system.impl.operation.getJobInterval;

import com.omgservers.model.job.JobQualifierEnum;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetJobIntervalOperationImpl implements GetJobIntervalOperation {

    @Override
    public Integer getJobIntervalInSeconds(JobQualifierEnum type) {
        // TODO: move to settings/properties?
        return switch (type) {
            case TENANT, PROJECT, STAGE -> 60;
            case HANDLER, MATCHMAKER, MATCH, RUNTIME -> 1;
        };
    }
}
