package com.omgservers.service.module.system.impl.service.taskService.impl.method.executeSchedulerTask;

import com.omgservers.schema.service.system.job.ViewJobsRequest;
import com.omgservers.schema.service.system.task.ExecuteMatchmakerTaskRequest;
import com.omgservers.schema.service.system.task.ExecutePoolTaskRequest;
import com.omgservers.schema.service.system.task.ExecuteRuntimeTaskRequest;
import com.omgservers.schema.service.system.task.ExecuteTenantTaskRequest;
import com.omgservers.schema.model.job.JobModel;
import com.omgservers.service.module.system.SystemModule;
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

    final SystemModule systemModule;

    public Uni<Boolean> executeTask() {
        return systemModule.getJobService().viewJobs(new ViewJobsRequest())
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
            case TENANT -> systemModule.getTaskService()
                    .executeTenantTask(new ExecuteTenantTaskRequest(job.getEntityId()))
                    .replaceWithVoid();
            case MATCHMAKER -> systemModule.getTaskService()
                    .executeMatchmakerTask(new ExecuteMatchmakerTaskRequest(job.getEntityId()))
                    .replaceWithVoid();
            case RUNTIME -> systemModule.getTaskService()
                    .executeRuntimeTask(new ExecuteRuntimeTaskRequest(job.getEntityId()))
                    .replaceWithVoid();
            case POOL -> systemModule.getTaskService()
                    .executePoolTask(new ExecutePoolTaskRequest(job.getEntityId()))
                    .replaceWithVoid();
        };
    }
}
