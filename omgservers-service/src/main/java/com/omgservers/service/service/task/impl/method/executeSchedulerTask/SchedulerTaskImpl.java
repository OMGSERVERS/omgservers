package com.omgservers.service.service.task.impl.method.executeSchedulerTask;

import com.omgservers.schema.model.job.JobModel;
import com.omgservers.service.service.job.JobService;
import com.omgservers.service.service.job.dto.ViewJobsRequest;
import com.omgservers.service.service.job.dto.ViewJobsResponse;
import com.omgservers.service.service.task.Task;
import com.omgservers.service.service.task.TaskService;
import com.omgservers.service.service.task.dto.ExecuteDeploymentTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteMatchmakerTaskRequest;
import com.omgservers.service.service.task.dto.ExecutePoolTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteRuntimeTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteStageTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteTenantTaskRequest;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class SchedulerTaskImpl implements Task<SchedulerTaskArguments> {

    final TaskService taskService;
    final JobService jobService;

    public Uni<Boolean> execute(final SchedulerTaskArguments taskArguments) {
        return viewJobs()
                .flatMap(jobs -> Multi.createFrom().iterable(jobs)
                        .onItem().transformToUniAndMerge(this::executeJob)
                        .collect().asList())
                .repeat().withDelay(Duration.ofSeconds(1)).indefinitely()
                .collect().last()
                .replaceWith(Boolean.TRUE);
    }

    Uni<List<JobModel>> viewJobs() {
        return jobService.viewJobs(new ViewJobsRequest())
                .map(ViewJobsResponse::getJobs);
    }

    Uni<Void> executeJob(final JobModel job) {
        return (switch (job.getQualifier()) {
            case TENANT -> taskService
                    .execute(new ExecuteTenantTaskRequest(job.getEntityId()));
            case STAGE -> taskService
                    .execute(new ExecuteStageTaskRequest(job.getShardKey(), job.getEntityId()));
            case DEPLOYMENT -> taskService
                    .execute(new ExecuteDeploymentTaskRequest(job.getEntityId()));
            case MATCHMAKER -> taskService
                    .execute(new ExecuteMatchmakerTaskRequest(job.getEntityId()));
            case RUNTIME -> taskService
                    .execute(new ExecuteRuntimeTaskRequest(job.getEntityId()));
            case POOL -> taskService
                    .execute(new ExecutePoolTaskRequest(job.getEntityId()));
        }).replaceWithVoid();
    }
}
