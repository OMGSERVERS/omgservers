package com.omgservers.service.server.service.task.impl.method.executeSchedulerTask;

import com.omgservers.schema.model.job.JobModel;
import com.omgservers.schema.service.system.job.ViewJobsRequest;
import com.omgservers.schema.service.system.task.ExecuteMatchmakerTaskRequest;
import com.omgservers.schema.service.system.task.ExecutePoolTaskRequest;
import com.omgservers.schema.service.system.task.ExecuteRuntimeTaskRequest;
import com.omgservers.schema.service.system.task.ExecuteTenantTaskRequest;
import com.omgservers.service.server.service.job.JobService;
import com.omgservers.service.server.service.task.TaskService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class SchedulerTaskImpl {

    final TaskService taskService;
    final JobService jobService;

    public Uni<Boolean> executeTask() {
        return jobService.viewJobs(new ViewJobsRequest())
                .flatMap(response -> Multi.createFrom().iterable(response.getJobs())
                        .onItem().transformToUniAndMerge(this::executeJob)
                        .collect().asList()
                )
                .repeat().withDelay(Duration.ofSeconds(1)).indefinitely()
                .collect().last()
                .replaceWith(Boolean.TRUE);
    }

    Uni<Void> executeJob(final JobModel job) {
        return switch (job.getQualifier()) {
            case TENANT -> taskService
                    .executeTenantTask(new ExecuteTenantTaskRequest(job.getEntityId()))
                    .replaceWithVoid();
            case MATCHMAKER -> taskService
                    .executeMatchmakerTask(new ExecuteMatchmakerTaskRequest(job.getEntityId()))
                    .replaceWithVoid();
            case RUNTIME -> taskService
                    .executeRuntimeTask(new ExecuteRuntimeTaskRequest(job.getEntityId()))
                    .replaceWithVoid();
            case POOL -> taskService
                    .executePoolTask(new ExecutePoolTaskRequest(job.getEntityId()))
                    .replaceWithVoid();
        };
    }
}
