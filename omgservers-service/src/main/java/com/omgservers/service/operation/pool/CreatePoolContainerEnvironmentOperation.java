package com.omgservers.service.operation.pool;

import com.omgservers.schema.model.poolContainer.PoolContainerEnvironmentEnum;
import com.omgservers.schema.model.runtime.RuntimeModel;
import io.smallrye.mutiny.Uni;

import java.util.HashMap;

public interface CreatePoolContainerEnvironmentOperation {
    Uni<HashMap<PoolContainerEnvironmentEnum, String>> execute(RuntimeModel runtime, String password);
}
