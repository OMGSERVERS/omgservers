package com.omgservers.schema.shard.runtime.runtimeAssignment;

import com.omgservers.schema.shard.ShardRequest;
import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncRuntimeAssignmentRequest implements ShardRequest {

    @NotNull
    RuntimeAssignmentModel runtimeAssignment;

    @Override
    public String getRequestShardKey() {
        return runtimeAssignment.getRuntimeId().toString();
    }
}
