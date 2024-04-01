package com.omgservers.model.poolRuntimeAssignment;

import com.omgservers.model.poolRuntimeServerContainerRequest.PoolRuntimeServerContainerRequestModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PoolRuntimeAssignmentConfigModel {

    static public PoolRuntimeAssignmentConfigModel create() {
        final var config = new PoolRuntimeAssignmentConfigModel();
        return config;
    }

    @NotNull
    PoolRuntimeServerContainerRequestModel request;
}
