package com.omgservers.service.module.system.impl.service.taskService.impl.method.executeSchedulerTask;

import com.omgservers.model.dto.system.job.ViewJobsRequest;
import com.omgservers.model.dto.system.task.ExecuteMatchmakerTaskRequest;
import com.omgservers.model.dto.system.task.ExecutePoolTaskRequest;
import com.omgservers.model.dto.system.task.ExecuteRuntimeTaskRequest;
import com.omgservers.model.dto.system.task.ExecuteTenantTaskRequest;
import com.omgservers.model.job.JobModel;
import com.omgservers.service.module.system.SystemModule;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
