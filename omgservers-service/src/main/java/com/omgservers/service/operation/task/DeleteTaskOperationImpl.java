package com.omgservers.service.operation.task;

import com.omgservers.schema.master.task.DeleteTaskRequest;
import com.omgservers.schema.master.task.DeleteTaskResponse;
import com.omgservers.schema.master.task.FindTaskRequest;
import com.omgservers.schema.master.task.FindTaskResponse;
import com.omgservers.schema.model.task.TaskModel;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.master.task.TaskMaster;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeleteTaskOperationImpl implements DeleteTaskOperation {

    final TaskMaster taskMaster;

    @Override
    public Uni<Void> execute(final Long shardKey, final Long entityId) {
        return findJob(shardKey, entityId)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithNull()
                .onItem().ifNotNull().transformToUni(job -> deleteJob(job.getId()))
                .replaceWithVoid();
    }

    @Override
    public Uni<Void> execute(Long entityId) {
        return execute(entityId, entityId);
    }

    Uni<TaskModel> findJob(final Long shardKey, final Long entityId) {
        final var request = new FindTaskRequest(shardKey, entityId);
        return taskMaster.getService().execute(request)
                .map(FindTaskResponse::getTask);
    }

    Uni<Boolean> deleteJob(final Long id) {
        final var request = new DeleteTaskRequest(id);
        return taskMaster.getService().execute(request)
                .map(DeleteTaskResponse::getDeleted);
    }
}
