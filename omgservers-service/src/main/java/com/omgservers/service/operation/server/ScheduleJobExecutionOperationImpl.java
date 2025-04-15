package com.omgservers.service.operation.server;

import com.omgservers.service.configuration.JobQualifierEnum;
import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.ScheduledExecution;
import io.quarkus.scheduler.Scheduler;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.function.Function;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ScheduleJobExecutionOperationImpl implements ScheduleJobExecutionOperation {

    static private final String JOB_INTERVAL = "1s";

    final TryAdvisoryLockOperation tryAdvisoryLockOperation;
    final PutIntoMdcOperation putIntoMdcOperation;

    final Scheduler scheduler;
    final PgPool pgPool;

    @Override
    public void execute(final JobQualifierEnum jobQualifier,
                        final Function<ScheduledExecution, Uni<Boolean>> asyncTask) {
        final var jobIdentity = jobQualifier.getQualifier();
        final var trigger = scheduler.newJob(jobIdentity)
                .setInterval(JOB_INTERVAL)
                .setConcurrentExecution(Scheduled.ConcurrentExecution.SKIP)
                .setAsyncTask(scheduledExecution -> {
                    putIntoMdcOperation.putArbitrarySubject(jobIdentity);

                    return pgPool.withTransaction(sqlConnection ->
                            tryAdvisoryLockOperation.execute(sqlConnection, jobQualifier.getLock())
                                    .flatMap(acquired -> {
                                        if (acquired) {
                                            return asyncTask.apply(scheduledExecution)
                                                    .invoke(finished -> {
                                                        if (finished) {
                                                            scheduler.unscheduleJob(jobIdentity);
                                                            log.info("Job \"{}\" unscheduled because it finished",
                                                                    jobQualifier);
                                                        }
                                                    })
                                                    .replaceWithVoid();
                                        } else {
                                            return Uni.createFrom().voidItem();
                                        }
                                    }))
                            .invoke(voidItem -> MDC.clear());
                })
                .schedule();

        log.info("Job \"{}\" scheduled, {}", jobQualifier, trigger);
    }
}
