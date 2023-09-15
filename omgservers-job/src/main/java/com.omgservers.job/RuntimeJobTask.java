package com.omgservers.job;

import com.omgservers.dto.runtime.DoRuntimeUpdateRequest;
import com.omgservers.model.job.JobTypeEnum;
import com.omgservers.module.system.impl.service.jobService.impl.JobTask;
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
    public JobTypeEnum getJobType() {
        return JobTypeEnum.RUNTIME;
    }

    @Override
    public Uni<Boolean> executeTask(Long shardKey, Long entity) {
        final var request = new DoRuntimeUpdateRequest(entity);
        return runtimeModule.getRuntimeService().doRuntimeUpdate(request)
                .replaceWith(true);
    }
}
