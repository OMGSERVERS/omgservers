package com.omgservers.schema.module.runtime.runtimeAssignment;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncRuntimeAssignmentRequest implements ShardedRequest {

    @NotNull
    RuntimeAssignmentModel runtimeAssignment;

    @Override
    public String getRequestShardKey() {
        return runtimeAssignment.getRuntimeId().toString();
    }
}
