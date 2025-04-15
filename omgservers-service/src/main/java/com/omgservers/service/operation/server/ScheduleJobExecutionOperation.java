package com.omgservers.service.operation.server;

import com.omgservers.service.configuration.JobQualifierEnum;
import io.quarkus.scheduler.ScheduledExecution;
import io.smallrye.mutiny.Uni;

import java.util.function.Function;

public interface ScheduleJobExecutionOperation {

    void execute(JobQualifierEnum jobQualifier,
                 Function<ScheduledExecution, Uni<Boolean>> asyncTask);
}
