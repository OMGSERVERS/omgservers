package com.omgservers.schema.shard.runtime.runtimeState;

import com.omgservers.schema.model.runtimeChangeOfState.RuntimeChangeOfStateDto;
import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRuntimeStateRequest implements ShardRequest {

    @NotNull
    Long runtimeId;

    @NotNull
    RuntimeChangeOfStateDto runtimeChangeOfState;

    @Override
    public String getRequestShardKey() {
        return runtimeId.toString();
    }
}
