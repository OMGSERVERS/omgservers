package com.omgservers.model.dto.runtime;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.runtimeAssignment.RuntimeAssignmentModel;
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
