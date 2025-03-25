package com.omgservers.service.operation.pool;

import com.omgservers.schema.model.deployment.DeploymentModel;
import com.omgservers.schema.model.runtime.RuntimeModel;
import io.smallrye.mutiny.Uni;

public interface CreatePoolRequestOperation {
    Uni<Boolean> execute(RuntimeModel runtime,
                         DeploymentModel deployment,
                         String idempotencyKey);
}
