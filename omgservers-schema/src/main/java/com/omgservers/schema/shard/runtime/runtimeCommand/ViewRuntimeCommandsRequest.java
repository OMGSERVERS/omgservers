package com.omgservers.schema.shard.runtime.runtimeCommand;

import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewRuntimeCommandsRequest implements ShardRequest {

    @NotNull
    Long runtimeId;

    @Override
    public String getRequestShardKey() {
        return runtimeId.toString();
    }
}
