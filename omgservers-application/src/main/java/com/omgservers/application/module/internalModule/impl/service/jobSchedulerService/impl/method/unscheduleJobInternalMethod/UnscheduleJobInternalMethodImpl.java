package com.omgservers.application.module.internalModule.impl.service.jobSchedulerService.impl.method.unscheduleJobInternalMethod;

import com.omgservers.application.module.internalModule.impl.operation.getJobNameOperation.GetJobNameOperation;
import com.omgservers.application.module.internalModule.impl.service.jobSchedulerService.request.UnscheduleJobInternalRequest;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.LogHelpService;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.request.SyncLogHelpRequest;
import com.omgservers.application.module.internalModule.model.log.LogModelFactory;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import io.quarkus.scheduler.Scheduler;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UnscheduleJobInternalMethodImpl implements UnscheduleJobInternalMethod {

    final LogHelpService logHelpService;

    final CheckShardOperation checkShardOperation;
    final GetJobNameOperation getJobNameOperation;

    final LogModelFactory logModelFactory;
    final Scheduler scheduler;

    @Override
    public Uni<Void> unscheduleJob(final UnscheduleJobInternalRequest request) {
        UnscheduleJobInternalRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var shardKey = request.getShardKey();
                    final var entity = request.getEntity();
                    return Uni.createFrom().voidItem()
                            .invoke(voidItem -> unscheduleJob(shardKey, entity))
                            .call(voidItem -> {
                                final var syncLog = logModelFactory
                                        .create(String.format("Job was unscheduled, entity=%d", entity));
                                final var syncLogHelpRequest = new SyncLogHelpRequest(syncLog);
                                return logHelpService.syncLog(syncLogHelpRequest);
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
