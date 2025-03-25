package com.omgservers.service.operation.runtime;

import io.smallrye.mutiny.Uni;

public interface DeleteRuntimeMessagesOperation {
    Uni<Void> execute(Long runtimeId);
}
