package com.omgservers.schema.module.runtime.runtimeCommand;

import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewRuntimeCommandsRequest implements ShardedRequest {

    @NotNull
    Long runtimeId;

    @Override
    public String getRequestShardKey() {
        return runtimeId.toString();
    }
}
