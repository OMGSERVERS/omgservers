package com.omgservers.connector.operation;

import com.omgservers.connector.configuration.JobQualifierEnum;
import io.quarkus.scheduler.ScheduledExecution;
import io.smallrye.mutiny.Uni;

import java.util.function.Function;

public interface ScheduleJobExecutionOperation {

    void execute(JobQualifierEnum jobQualifier,
                 Function<ScheduledExecution, Uni<Boolean>> asyncTask);
}
