package com.omgservers.module.system.impl.service.jobService.impl.method.unscheduleJob;

import com.omgservers.dto.internal.SyncLogRequest;
import com.omgservers.dto.internal.UnscheduleJobRequest;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.module.system.impl.operation.getJobName.GetJobNameOperation;
import com.omgservers.module.system.impl.service.logService.LogService;
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
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var shardKey = request.getShardKey();
                    final var entity = request.getEntity();
                    return Uni.createFrom().voidItem()
                            .invoke(voidItem -> unscheduleJob(shardKey, entity))
                            .call(voidItem -> {
                                final var syncLog = logModelFactory
                                        .create(String.format("Job was unscheduled, entity=%d", entity));
                                final var syncLogHelpRequest = new SyncLogRequest(syncLog);
                                return logService.syncLog(syncLogHelpRequest);
                            });
                });
    }

    void unscheduleJob(Long shardKey, Long entity) {
        final var jobName = getJobNameOperation.getJobName(shardKey, entity);
        final var trigger = scheduler.unscheduleJob(jobName);
        if (trigger == null) {
            log.warn("Job was not found, skip operation, job={}", jobName);
        }
    }
}
