package com.omgservers.application.module.internalModule.impl.service.jobSchedulerService.impl.method.scheduleJobInternalMethod;

import com.omgservers.application.module.internalModule.impl.operation.getJobIntervalOperation.GetJobIntervalOperation;
import com.omgservers.application.module.internalModule.impl.operation.getJobNameOperation.GetJobNameOperation;
import com.omgservers.application.module.internalModule.impl.service.jobSchedulerService.impl.JobTask;
import com.omgservers.application.module.internalModule.impl.service.jobSchedulerService.request.ScheduleJobInternalRequest;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.LogHelpService;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.request.SyncLogHelpRequest;
import com.omgservers.application.module.internalModule.model.job.JobType;
import com.omgservers.application.module.internalModule.model.log.LogModelFactory;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
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
class ScheduleJobInternalMethodImpl implements ScheduleJobInternalMethod {

    final LogHelpService logHelpService;

    final GetJobIntervalOperation getJobIntervalOperation;
    final CheckShardOperation checkShardOperation;
    final GetJobNameOperation getJobNameOperation;

    final LogModelFactory logModelFactory;

    final Map<JobType, JobTask> jobTasks;
    final Scheduler scheduler;

    public ScheduleJobInternalMethodImpl(LogHelpService logHelpService,
                                         CheckShardOperation checkShardOperation,
                                         GetJobNameOperation getJobNameOperation,
                                         GetJobIntervalOperation getJobIntervalOperation,
                                         LogModelFactory logModelFactory,
                                         Instance<JobTask> jobTaskBeans,
                                         Scheduler scheduler) {
        this.logHelpService = logHelpService;
        this.checkShardOperation = checkShardOperation;
        this.getJobNameOperation = getJobNameOperation;
        this.getJobIntervalOperation = getJobIntervalOperation;
        this.logModelFactory = logModelFactory;
        this.jobTasks = new ConcurrentHashMap<>();
        jobTaskBeans.stream().forEach(jobTask -> {
            JobType type = jobTask.getJobType();
            jobTasks.put(type, jobTask);
            log.info("Job task added, type={}, jobTask={}", type, jobTask.getClass().getSimpleName());
        });
        this.scheduler = scheduler;
    }

    @Override
    public Uni<Void> scheduleJob(ScheduleJobInternalRequest request) {
        ScheduleJobInternalRequest.validate(request);

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
                                final var syncLogHelpRequest = new SyncLogHelpRequest(syncLog);
                                return logHelpService.syncLog(syncLogHelpRequest);
                            });
                });
    }

    void scheduleJob(Long shardKey, Long entity, JobType type) {
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
                        final JobType type) {
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
