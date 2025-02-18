package com.omgservers.service.operation.runtime;

import com.omgservers.schema.model.poolSeverContainer.PoolContainerLabel;
import com.omgservers.schema.model.runtime.RuntimeModel;
import io.smallrye.mutiny.Uni;

import java.util.Map;

public interface CreateRuntimeLabelsForContainerOperation {
    Uni<Map<PoolContainerLabel, String>> execute(RuntimeModel runtime);
}
