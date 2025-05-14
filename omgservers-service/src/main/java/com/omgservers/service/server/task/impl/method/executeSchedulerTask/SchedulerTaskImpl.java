package com.omgservers.service.server.task.impl.method.executeSchedulerTask;

import com.omgservers.schema.master.task.ViewTasksRequest;
import com.omgservers.schema.master.task.ViewTasksResponse;
import com.omgservers.schema.model.task.TaskModel;
import com.omgservers.service.master.task.TaskMaster;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import com.omgservers.service.server.task.Task;
import com.omgservers.service.server.task.TaskService;
import com.omgservers.service.server.task.dto.ExecuteDeploymentTaskRequest;
import com.omgservers.service.server.task.dto.ExecuteMatchmakerTaskRequest;
import com.omgservers.service.server.task.dto.ExecutePoolTaskRequest;
import com.omgservers.service.server.task.dto.ExecuteRuntimeTaskRequest;
import com.omgservers.service.server.task.dto.ExecuteStageTaskRequest;
import com.omgservers.service.server.task.dto.ExecuteTenantTaskRequest;
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
    final TaskMaster taskMaster;

    final GetServiceConfigOperation getServiceConfigOperation;

    public Uni<Boolean> execute(final SchedulerTaskArguments taskArguments) {
        return viewJobs()
                .flatMap(jobs -> Multi.createFrom().iterable(jobs)
                        .onItem().transformToUniAndMerge(this::executeJob)
                        .collect().asList())
                .repeat().withDelay(Duration.ofSeconds(1)).atMost(10)
                .collect().last()
                .replaceWith(Boolean.TRUE);
    }

    Uni<List<TaskModel>> viewJobs() {
        final var thisUri = getServiceConfigOperation.getServiceConfig().shard().uri();
        final var request = new ViewTasksRequest(thisUri);
        return taskMaster.getService().execute(request)
                .map(ViewTasksResponse::getTasks);
    }

    Uni<Void> executeJob(final TaskModel job) {
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
