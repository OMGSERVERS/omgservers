package com.omgservers.service.operation.runtime;

import io.smallrye.mutiny.Uni;

public interface DeleteRuntimeCommandsOperation {
    Uni<Void> execute(Long runtimeId);
}
