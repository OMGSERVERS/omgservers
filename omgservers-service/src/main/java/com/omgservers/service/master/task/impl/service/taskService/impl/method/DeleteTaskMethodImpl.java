package com.omgservers.service.master.task.impl.service.taskService.impl.method;

import com.omgservers.schema.master.task.DeleteTaskRequest;
import com.omgservers.schema.master.task.DeleteTaskResponse;
import com.omgservers.service.master.task.impl.operation.DeleteTaskOperation;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeleteTaskMethodImpl implements DeleteTaskMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteTaskOperation deleteTaskOperation;

    @Override
    public Uni<DeleteTaskResponse> execute(final DeleteTaskRequest request) {
        log.debug("{}", request);

        final var id = request.getId();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deleteTaskOperation.execute(changeContext, sqlConnection, id))
                .map(ChangeContext::getResult)
                .map(DeleteTaskResponse::new);
    }
}
