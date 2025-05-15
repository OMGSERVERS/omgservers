package com.omgservers.service.server.task.impl.method.executeSchedulerTask;

import com.omgservers.schema.master.task.ViewTasksRequest;
import com.omgservers.schema.master.task.ViewTasksResponse;
import com.omgservers.schema.model.task.TaskModel;
import com.omgservers.service.master.task.TaskMaster;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import com.omgservers.service.server.task.Task;
import com.omgservers.service.server.task.TaskResult;
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

    public Uni<TaskResult> execute(final SchedulerTaskArguments taskArguments) {
        return viewTasks()
                .flatMap(this::executeAll)
                .flatMap(tasksToRepeat -> {
                    if (tasksToRepeat.isEmpty()) {
                        return Uni.createFrom().voidItem();
                    } else {
                        return executeAll(tasksToRepeat)
                                .onItem().delayIt().by(Duration.ofMillis(250))
                                .flatMap(this::executeAll)
                                .onItem().delayIt().by(Duration.ofMillis(250))
                                .flatMap(this::executeAll);
                    }
                })
                .replaceWith(TaskResult.DONE);
    }

    Uni<List<TaskModel>> viewTasks() {
        final var thisUri = getServiceConfigOperation.getServiceConfig().shard().uri();
        final var request = new ViewTasksRequest(thisUri);
        return taskMaster.getService().execute(request)
                .map(ViewTasksResponse::getTasks);
    }

    Uni<List<TaskModel>> executeAll(final List<TaskModel> tasks) {
        return Multi.createFrom().iterable(tasks)
                .onItem().transformToUniAndMerge(this::executeTask)
                .collect().asList()
                .map(results -> results.stream()
                        .filter(ExecuteTaskResult::repeat)
                        .map(ExecuteTaskResult::task)
                        .toList());
    }

    Uni<ExecuteTaskResult> executeTask(final TaskModel task) {
        return (switch (task.getQualifier()) {
            case TENANT -> taskService
                    .execute(new ExecuteTenantTaskRequest(task.getEntityId()))
                    .map(ExecuteTenantTaskResponse::getTaskResult)
                    .map(taskResult -> new ExecuteTaskResult(task, false));
            case STAGE -> taskService
                    .execute(new ExecuteStageTaskRequest(task.getShardKey(), task.getEntityId()))
                    .map(ExecuteStageTaskResponse::getTaskResult)
                    .map(taskResult -> new ExecuteTaskResult(task, false));
            case DEPLOYMENT -> taskService
                    .execute(new ExecuteDeploymentTaskRequest(task.getEntityId()))
                    .map(ExecuteDeploymentTaskResponse::getTaskResult)
                    .map(taskResult -> new ExecuteTaskResult(task,
                            taskResult.equals(TaskResult.DONE)));
            case MATCHMAKER -> taskService
                    .execute(new ExecuteMatchmakerTaskRequest(task.getEntityId()))
                    .map(ExecuteMatchmakerTaskResponse::getTaskResult)
                    .map(taskResult -> new ExecuteTaskResult(task,
                            taskResult.equals(TaskResult.DONE)));
            case RUNTIME -> taskService
                    .execute(new ExecuteRuntimeTaskRequest(task.getEntityId()))
                    .map(ExecuteRuntimeTaskResponse::getTaskResult)
                    .map(taskResult -> new ExecuteTaskResult(task, false));
            case POOL -> taskService
                    .execute(new ExecutePoolTaskRequest(task.getEntityId()))
                    .map(ExecutePoolTaskResponse::getTaskResult)
                    .map(taskResult -> new ExecuteTaskResult(task, false));
        });
    }

    record ExecuteTaskResult(TaskModel task, boolean repeat) {
    }
}
