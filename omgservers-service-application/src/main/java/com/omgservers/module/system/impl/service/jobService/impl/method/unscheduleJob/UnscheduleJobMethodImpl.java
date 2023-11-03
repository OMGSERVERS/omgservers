package com.omgservers.module.system.impl.service.jobService.impl.method.unscheduleJob;

import com.omgservers.model.dto.system.SyncLogRequest;
import com.omgservers.model.dto.system.UnscheduleJobRequest;
import com.omgservers.module.system.impl.operation.getJobName.GetJobNameOperation;
import com.omgservers.module.system.impl.service.logService.LogService;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.quarkus.scheduler.Scheduler;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UnscheduleJobMethodImpl implements UnscheduleJobMethod {

    final LogService logService;

    final CheckShardOperation checkShardOperation;
    final GetJobNameOperation getJobNameOperation;

    final LogModelFactory logModelFactory;
    final Scheduler scheduler;

    @Override
    public Uni<Void> unscheduleJob(final UnscheduleJobRequest request) {
        final var shardKey = request.getShardKey();
        final var entityId = request.getEntityId();
        return Uni.createFrom().voidItem()
                .invoke(voidItem -> unscheduleJob(shardKey, entityId))
                .call(voidItem -> {
                    final var syncLog = logModelFactory
                            .create(String.format("Job was unscheduled, entityId=%d", entityId));
                    final var syncLogHelpRequest = new SyncLogRequest(syncLog);
                    return logService.syncLog(syncLogHelpRequest);
                });
    }

    void unscheduleJob(Long shardKey, Long entityId) {
        final var jobName = getJobNameOperation.getJobName(shardKey, entityId);
        final var trigger = scheduler.unscheduleJob(jobName);
        if (trigger == null) {
            log.warn("Job was not found, skip operation, job={}", jobName);
        }
    }
}
