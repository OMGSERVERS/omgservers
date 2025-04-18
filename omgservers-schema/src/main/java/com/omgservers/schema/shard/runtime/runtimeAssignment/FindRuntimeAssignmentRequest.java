package com.omgservers.schema.shard.runtime.runtimeAssignment;

import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindRuntimeAssignmentRequest implements ShardRequest {

    @NotNull
    Long runtimeId;

    @NotNull
    Long clientId;

    @Override
    public String getRequestShardKey() {
        return runtimeId.toString();
    }
}
