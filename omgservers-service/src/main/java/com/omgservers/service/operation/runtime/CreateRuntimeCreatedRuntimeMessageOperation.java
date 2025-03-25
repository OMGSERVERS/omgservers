package com.omgservers.service.operation.runtime;

import com.omgservers.schema.model.runtime.RuntimeModel;
import io.smallrye.mutiny.Uni;

public interface CreateRuntimeCreatedRuntimeMessageOperation {
    Uni<Boolean> execute(RuntimeModel runtime,
                         String idempotencyKey);
}
