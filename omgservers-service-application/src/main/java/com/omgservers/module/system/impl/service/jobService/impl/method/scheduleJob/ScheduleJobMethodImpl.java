package com.omgservers.module.system.impl.service.jobService.impl.method.scheduleJob;

import com.omgservers.model.dto.internal.ScheduleJobRequest;
import com.omgservers.model.dto.internal.SyncLogRequest;
import com.omgservers.model.job.JobQualifierEnum;
import com.omgservers.module.system.impl.operation.getJobInterval.GetJobIntervalOperation;
import com.omgservers.module.system.impl.operation.getJobName.GetJobNameOperation;
import com.omgservers.module.system.impl.service.logService.LogService;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.module.system.impl.service.jobService.impl.JobTask;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.ScheduledExecution;
import io.quarkus.scheduler.Scheduler;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
class ScheduleJobMethodImpl implements ScheduleJobMethod {

    final LogService logService;

    final GetJobIntervalOperation getJobIntervalOperation;
    final CheckShardOperation checkShardOperation;
    final GetJobNameOperation getJobNameOperation;

    final LogModelFactory logModelFactory;

    final Map<JobQualifierEnum, JobTask> jobTasks;
    final Scheduler scheduler;

    public ScheduleJobMethodImpl(LogService logService,
                                 CheckShardOperation checkShardOperation,
                                 GetJobNameOperation getJobNameOperation,
                                 GetJobIntervalOperation getJobIntervalOperation,
                                 LogModelFactory logModelFactory,
                                 Instance<JobTask> jobTaskBeans,
                                 Scheduler scheduler) {
        this.logService = logService;
        this.checkShardOperation = checkShardOperation;
        this.getJobNameOperation = getJobNameOperation;
        this.getJobIntervalOperation = getJobIntervalOperation;
        this.logModelFactory = logModelFactory;
        this.jobTasks = new ConcurrentHashMap<>();
        jobTaskBeans.stream().forEach(jobTask -> {
            JobQualifierEnum qualifier = jobTask.getJobQualifier();
            jobTasks.put(qualifier, jobTask);
        });
        this.scheduler = scheduler;
    }

    @Override
    public Uni<Void> scheduleJob(ScheduleJobRequest request) {
        final var shardKey = request.getShardKey();
        final var entityId = request.getEntityId();
        final var type = request.getType();
        return Uni.createFrom().voidItem()
                .invoke(voidItem -> scheduleJob(shardKey, entityId, type))
                .call(voidItem -> {
                    final var syncLog = logModelFactory
                            .create(String.format("Job was scheduled, type=%s, entityId=%d", type,
                                    entityId));
                    final var syncLogHelpRequest = new SyncLogRequest(syncLog);
                    return logService.syncLog(syncLogHelpRequest);
                });
    }

    void scheduleJob(Long shardKey, Long entityId, JobQualifierEnum type) {
        final var jobName = getJobNameOperation.getJobName(shardKey, entityId);
        if (scheduler.getScheduledJob(jobName) != null) {
            log.warn("Job task was already scheduled, job={}", jobName);
        } else {
            final var jobIntervalInSeconds = getJobIntervalOperation.getJobIntervalInSeconds(type);
            // Distribute jobs overs timeline
            final var jobDelayInSeconds = (int) (Math.random() * jobIntervalInSeconds);
            scheduler.newJob(jobName)
                    .setInterval(jobIntervalInSeconds + "s")
                    .setDelayed(jobDelayInSeconds + "s")
                    .setConcurrentExecution(Scheduled.ConcurrentExecution.SKIP)
                    .setAsyncTask(scheduledExecution -> asyncTask(scheduledExecution, shardKey, entityId, type))
                    .schedule();
        }
    }

    @WithSpan
    Uni<Void> asyncTask(final ScheduledExecution scheduledExecution,
                        final Long shardKey,
                        final Long entityId,
                        final JobQualifierEnum qualifier) {
        // TODO: calculate and log delay between launch and planning timestamp
        final var job = jobTasks.get(qualifier);
        if (job != null) {
            // TODO: check shard and reschedule in case of any rebalance
            return job.executeTask(shardKey, entityId)
                    .onFailure()
                    .recoverWithUni(t -> {
                        log.error("Job task failed, shardKey={}, entityId={}, qualifier={}, {}:{}",
                                shardKey, entityId, qualifier, t.getClass().getSimpleName(), t.getMessage());
                        return Uni.createFrom().voidItem();
                    });
        } else {
            log.error("Job task was not found, qualifier={}", qualifier);
            return Uni.createFrom().voidItem();
        }
    }
}
