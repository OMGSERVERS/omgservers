package com.omgservers.module.system.impl.service.jobService.impl.method.scheduleJob;

import com.omgservers.dto.internal.ScheduleJobRequest;
import com.omgservers.dto.internal.SyncLogRequest;
import com.omgservers.model.job.JobTypeEnum;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.module.system.impl.operation.getJobInterval.GetJobIntervalOperation;
import com.omgservers.module.system.impl.operation.getJobName.GetJobNameOperation;
import com.omgservers.module.system.impl.service.jobService.impl.JobTask;
import com.omgservers.module.system.impl.service.logService.LogService;
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

    final Map<JobTypeEnum, JobTask> jobTasks;
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
            JobTypeEnum type = jobTask.getJobType();
            jobTasks.put(type, jobTask);
            log.info("Job task added, type={}, jobTask={}", type, jobTask.getClass().getSimpleName());
        });
        this.scheduler = scheduler;
    }

    @Override
    public Uni<Void> scheduleJob(ScheduleJobRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var shardKey = request.getShardKey();
                    final var entity = request.getEntity();
                    final var type = request.getType();
                    return Uni.createFrom().voidItem()
                            .invoke(voidItem -> scheduleJob(shardKey, entity, type))
                            .call(voidItem -> {
                                final var syncLog = logModelFactory
                                        .create(String.format("Job was scheduled, type=%s, entity=%d", type, entity));
                                final var syncLogHelpRequest = new SyncLogRequest(syncLog);
                                return logService.syncLog(syncLogHelpRequest);
                            });
                });
    }

    void scheduleJob(Long shardKey, Long entity, JobTypeEnum type) {
        final var jobName = getJobNameOperation.getJobName(shardKey, entity);
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
                    .setAsyncTask(scheduledExecution -> asyncTask(scheduledExecution, shardKey, entity, type))
                    .schedule();

            log.info("Job task scheduled, interval={}, delay={}, job={}",
                    jobIntervalInSeconds, jobDelayInSeconds, jobName);
        }
    }

    @WithSpan
    Uni<Void> asyncTask(final ScheduledExecution scheduledExecution,
                        final Long shardKey,
                        final Long entity,
                        final JobTypeEnum type) {
        // TODO: calculate and log delay between launch and planning timestamp
        log.info("Job was launched, shardKey={}, entity={}, type={}", shardKey, entity, type);
        final var job = jobTasks.get(type);
        if (job != null) {
            // TODO: check shard and reschedule in case of any rebalance
            return job.executeTask(shardKey, entity)
                    .invoke(result -> {
                        if (!result) {
                            final var jobName = getJobNameOperation.getJobName(shardKey, entity);
                            final var trigger = scheduler.unscheduleJob(jobName);
                            if (trigger == null) {
                                log.warn("Job task return false, but job was not found to unschedule, job={}", jobName);
                            } else {
                                log.info("Job task return false and was unscheduled, job={}", jobName);
                            }
                        }
                    })
                    .replaceWithVoid()
                    .onFailure()
                    .recoverWithUni(t -> {
                        log.error("Job task failed, shardKey={}, entity={}, type={}, {}",
                                shardKey, entity, type, t.getMessage());
                        return Uni.createFrom().voidItem();
                    });
        } else {
            log.warn("Job task was not found, type={}", type);
            return Uni.createFrom().voidItem();
        }
    }
}
