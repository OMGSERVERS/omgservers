package com.omgservers.dispatcher.operation;

import com.omgservers.dispatcher.configuration.JobQualifierEnum;
import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.ScheduledExecution;
import io.quarkus.scheduler.Scheduler;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.function.Function;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ScheduleJobExecutionOperationImpl implements ScheduleJobExecutionOperation {

    final PutIntoMdcOperation putIntoMdcOperation;

    final Scheduler scheduler;

    @Override
    public void execute(final JobQualifierEnum jobQualifier,
                        final Function<ScheduledExecution, Uni<Boolean>> asyncTask) {
        final var jobIdentity = jobQualifier.getQualifier();
        final var jobInterval = jobQualifier.getInterval();
        final var trigger = scheduler.newJob(jobIdentity)
                .setInterval(jobInterval)
                .setConcurrentExecution(Scheduled.ConcurrentExecution.SKIP)
                .setAsyncTask(scheduledExecution -> {
                    putIntoMdcOperation.putArbitrarySubject(jobIdentity);

                    return asyncTask.apply(scheduledExecution)
                            .invoke(finished -> {
                                if (finished) {
                                    scheduler.unscheduleJob(jobIdentity);
                                    log.info("Job \"{}\" unscheduled because it finished",
                                            jobQualifier);
                                }
                            })
                            .replaceWithVoid()
                            .invoke(voidItem -> MDC.clear());
                })
                .schedule();

        log.info("Job \"{}\" scheduled, {}", jobQualifier, trigger);
    }
}
