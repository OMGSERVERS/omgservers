package com.omgservers.application.jobs;

import com.omgservers.dto.runtime.DoUpdateShardedRequest;
import com.omgservers.model.job.JobType;
import com.omgservers.module.internal.impl.service.jobShardedService.impl.JobTask;
import com.omgservers.module.runtime.RuntimeModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RuntimeJobTask implements JobTask {

    final RuntimeModule runtimeModule;

    @Override
    public JobType getJobType() {
        return JobType.RUNTIME;
    }

    @Override
    public Uni<Boolean> executeTask(Long shardKey, Long entity) {
        final var request = new DoUpdateShardedRequest(entity);
        return runtimeModule.getRuntimeShardedService().doUpdate(request)
                .replaceWith(true);
    }
}
