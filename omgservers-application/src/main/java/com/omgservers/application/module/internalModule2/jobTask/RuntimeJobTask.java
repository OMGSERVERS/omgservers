package com.omgservers.application.module.internalModule2.jobTask;

import com.omgservers.base.module.internal.impl.service.jobRoutedService.impl.JobTask;
import com.omgservers.application.module.runtimeModule.RuntimeModule;
import com.omgservers.dto.runtimeModule.DoUpdateRoutedRequest;
import com.omgservers.model.job.JobType;
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
        final var request = new DoUpdateRoutedRequest(entity);
        return runtimeModule.getRuntimeInternalService().doUpdate(request)
                .replaceWith(true);
    }
}
