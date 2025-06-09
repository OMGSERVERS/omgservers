package com.omgservers.service.server.task.impl.method.executeSchedulerTask;

import com.omgservers.schema.master.task.ViewTasksRequest;
import com.omgservers.schema.master.task.ViewTasksResponse;
import com.omgservers.schema.model.task.TaskModel;
import com.omgservers.service.master.task.TaskMaster;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import com.omgservers.service.server.task.Task;
import com.omgservers.service.server.task.TaskService;
import com.omgservers.service.server.task.dto.ExecuteDeploymentTaskRequest;
import com.omgservers.service.server.task.dto.ExecuteDeploymentTaskResponse;
import com.omgservers.service.server.task.dto.ExecuteMatchmakerTaskRequest;
import com.omgservers.service.server.task.dto.ExecuteMatchmakerTaskResponse;
import com.omgservers.service.server.task.dto.ExecutePoolTaskRequest;
import com.omgservers.service.server.task.dto.ExecutePoolTaskResponse;
import com.omgservers.service.server.task.dto.ExecuteRuntimeTaskRequest;
import com.omgservers.service.server.task.dto.ExecuteRuntimeTaskResponse;
import com.omgservers.service.server.task.dto.ExecuteStageTaskRequest;
import com.omgservers.service.server.task.dto.ExecuteStageTaskResponse;
import com.omgservers.service.server.task.dto.ExecuteTenantTaskRequest;
import com.omgservers.service.server.task.dto.ExecuteTenantTaskResponse;
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
        return viewTasks()
                .flatMap(tasks -> Uni.createFrom().voidItem()
                        .flatMap(voidItem -> executeAll(tasks))
                        .onItem().delayIt().by(Duration.ofMillis(500))
                        .flatMap(voidItem -> executeAll(tasks)))
                .replaceWith(Boolean.FALSE);
    }

    Uni<List<TaskModel>> viewTasks() {
        final var thisUri = getServiceConfigOperation.getServiceConfig().shard().uri();
        final var request = new ViewTasksRequest(thisUri);
        return taskMaster.getService().execute(request)
                .map(ViewTasksResponse::getTasks);
    }

    Uni<Void> executeAll(final List<TaskModel> tasks) {
        return Multi.createFrom().iterable(tasks)
                .onItem().transformToUniAndMerge(this::executeTask)
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Boolean> executeTask(final TaskModel task) {
        return (switch (task.getQualifier()) {
            case TENANT -> taskService
                    .execute(new ExecuteTenantTaskRequest(task.getEntityId()))
                    .map(ExecuteTenantTaskResponse::getFinished);
            case STAGE -> taskService
                    .execute(new ExecuteStageTaskRequest(task.getShardKey(), task.getEntityId()))
                    .map(ExecuteStageTaskResponse::getFinished);
            case DEPLOYMENT -> taskService
                    .execute(new ExecuteDeploymentTaskRequest(task.getEntityId()))
                    .map(ExecuteDeploymentTaskResponse::getFinished);
            case MATCHMAKER -> taskService
                    .execute(new ExecuteMatchmakerTaskRequest(task.getEntityId()))
                    .map(ExecuteMatchmakerTaskResponse::getFinished);
            case RUNTIME -> taskService
                    .execute(new ExecuteRuntimeTaskRequest(task.getEntityId()))
                    .map(ExecuteRuntimeTaskResponse::getFinished);
            case POOL -> taskService
                    .execute(new ExecutePoolTaskRequest(task.getEntityId()))
                    .map(ExecutePoolTaskResponse::getFinished);
        });
    }
}
