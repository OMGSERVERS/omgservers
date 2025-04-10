package com.omgservers.service.operation.pool;

import com.omgservers.schema.model.deployment.DeploymentModel;
import com.omgservers.schema.model.poolContainer.PoolContainerLabel;
import com.omgservers.schema.model.runtime.RuntimeModel;
import io.smallrye.mutiny.Uni;

import java.util.Map;

public interface CreatePoolContainerLabelsOperation {
    Uni<Map<PoolContainerLabel, String>> execute(RuntimeModel runtime, DeploymentModel deployment);
}
