package com.omgservers.model.dto.pool.poolRuntimeAssignment;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.poolRuntimeAssignment.PoolRuntimeAssignmentModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncPoolRuntimeAssignmentRequest implements ShardedRequest {

    @NotNull
    PoolRuntimeAssignmentModel poolRuntimeAssignment;

    @Override
    public String getRequestShardKey() {
        return poolRuntimeAssignment.getPoolId().toString();
    }
}
