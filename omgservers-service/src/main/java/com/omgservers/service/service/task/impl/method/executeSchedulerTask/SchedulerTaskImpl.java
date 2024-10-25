package com.omgservers.service.service.task.impl.method.executeSchedulerTask;

import com.omgservers.schema.model.job.JobModel;
import com.omgservers.service.service.job.JobService;
import com.omgservers.service.service.job.dto.ViewJobsRequest;
import com.omgservers.service.service.task.TaskService;
import com.omgservers.service.service.task.dto.ExecuteBuildRequestTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteMatchmakerTaskRequest;
import com.omgservers.service.service.task.dto.ExecutePoolTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteRuntimeTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteTenantTaskRequest;
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

    public Uni<Boolean> execute() {
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
        return (switch (job.getQualifier()) {
            case TENANT -> taskService
                    .execute(new ExecuteTenantTaskRequest(job.getEntityId()));
            case MATCHMAKER -> taskService
                    .execute(new ExecuteMatchmakerTaskRequest(job.getEntityId()));
            case RUNTIME -> taskService
                    .execute(new ExecuteRuntimeTaskRequest(job.getEntityId()));
            case POOL -> taskService
                    .execute(new ExecutePoolTaskRequest(job.getEntityId()));
            case BUILD_REQUEST -> taskService
                    .execute(new ExecuteBuildRequestTaskRequest(job.getShardKey(),
                            job.getEntityId()));
        }).replaceWithVoid();
    }
}
