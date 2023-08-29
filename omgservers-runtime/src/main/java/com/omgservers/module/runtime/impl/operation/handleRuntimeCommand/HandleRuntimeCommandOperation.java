package com.omgservers.module.runtime.impl.operation.handleRuntimeCommand;

import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface HandleRuntimeCommandOperation {
    Uni<Boolean> handleRuntimeCommand(RuntimeCommandModel runtimeCommand);

    default Boolean handleRuntimeCommand(long timeout, RuntimeCommandModel runtimeCommand) {
        return handleRuntimeCommand(runtimeCommand)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
