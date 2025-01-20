package com.omgservers.service.operation.job.test;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ExecuteTaskOperationImpl implements ExecuteTaskOperation {

    @Override
    public Uni<Boolean> execute(Uni<Boolean> task) {
        return task.onFailure()
                .recoverWithUni(t -> {
                    log.warn("Job task failed, {}:{}", t.getClass().getSimpleName(), t.getMessage(), t);
                    return Uni.createFrom().item(Boolean.FALSE);
                })
                .invoke(result -> log.trace("Task finished, result={}", result));
    }
}
