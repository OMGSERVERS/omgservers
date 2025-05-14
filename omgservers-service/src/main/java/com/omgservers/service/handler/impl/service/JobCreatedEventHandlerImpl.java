package com.omgservers.service.handler.impl.service;

import com.omgservers.schema.master.task.GetTaskRequest;
import com.omgservers.schema.master.task.GetTaskResponse;
import com.omgservers.schema.model.task.TaskModel;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.system.TaskCreatedEventBodyModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.master.task.TaskMaster;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class JobCreatedEventHandlerImpl implements EventHandler {

    final TaskMaster taskMaster;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TASK_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (TaskCreatedEventBodyModel) event.getBody();
        final var taskId = body.getId();

        return getTask(taskId)
                .flatMap(task -> {
                    log.debug("Created, {}", task);
                    return Uni.createFrom().voidItem();
                })
                .replaceWithVoid();
    }

    Uni<TaskModel> getTask(final Long id) {
        final var request = new GetTaskRequest(id);
        return taskMaster.getService().execute(request)
                .map(GetTaskResponse::getTask);
    }
}
