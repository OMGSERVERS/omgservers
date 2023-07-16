package com.omgservers.application.module.internalModule.impl.service.jobSchedulerService.impl.jobTask;

import com.omgservers.application.module.internalModule.impl.service.jobSchedulerService.impl.JobTask;
import com.omgservers.application.module.internalModule.model.job.JobType;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RuntimeJobTask implements JobTask {

    @Override
    public JobType getJobType() {
        return JobType.RUNTIME;
    }

    @Override
    public Uni<Boolean> executeTask(UUID shardKey, UUID entity) {
        return Uni.createFrom().item(true);
    }
}
