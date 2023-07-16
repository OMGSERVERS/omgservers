package com.omgservers.application.module.internalModule.impl.operation.getJobIntervalOperation;

import com.omgservers.application.module.internalModule.model.job.JobType;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetJobIntervalOperationImpl implements GetJobIntervalOperation {

    @Override
    public Integer getJobIntervalInSeconds(JobType type) {
        // TODO: move to settings/properties?
        return switch (type) {
            case TENANT -> 60 * 60; // 1h
            case PROJECT -> 60; // 1m
            case STAGE -> 1; // 1s
            case MATCHMAKER, RUNTIME -> 1; // 1s
        };
    }
}
