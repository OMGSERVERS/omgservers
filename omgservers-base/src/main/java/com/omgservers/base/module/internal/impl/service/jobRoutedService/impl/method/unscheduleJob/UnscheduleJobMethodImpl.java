package com.omgservers.base.module.internal.impl.service.jobRoutedService.impl.method.unscheduleJob;

import com.omgservers.base.factory.LogModelFactory;
import com.omgservers.base.module.internal.impl.operation.getJobName.GetJobNameOperation;
import com.omgservers.base.module.internal.impl.service.logService.LogService;
import com.omgservers.dto.internalModule.SyncLogRequest;
import com.omgservers.base.operation.checkShard.CheckShardOperation;
import com.omgservers.dto.internalModule.UnscheduleJobRoutedRequest;
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
    public Uni<Void> unscheduleJob(final UnscheduleJobRoutedRequest request) {
        UnscheduleJobRoutedRequest.validate(request);

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
