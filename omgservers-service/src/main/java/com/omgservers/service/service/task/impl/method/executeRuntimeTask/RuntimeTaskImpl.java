package com.omgservers.service.service.task.impl.method.executeRuntimeTask;

import com.omgservers.service.service.task.Task;
import com.omgservers.service.service.task.impl.method.executeRuntimeTask.operation.FetchRuntimeOperation;
import com.omgservers.service.service.task.impl.method.executeRuntimeTask.operation.HandleRuntimeOperation;
import com.omgservers.service.service.task.impl.method.executeRuntimeTask.operation.UpdateRuntimeOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RuntimeTaskImpl implements Task<RuntimeTaskArguments> {

    final HandleRuntimeOperation handleRuntimeOperation;
    final UpdateRuntimeOperation updateRuntimeOperation;
    final FetchRuntimeOperation fetchRuntimeOperation;

    public Uni<Boolean> execute(final RuntimeTaskArguments taskArguments) {
        final var runtimeId = taskArguments.runtimeId();
        return fetchRuntimeOperation.execute(runtimeId)
                .map(handleRuntimeOperation::execute)
                .flatMap(updateRuntimeOperation::execute)
                .replaceWith(Boolean.TRUE);
    }
}
