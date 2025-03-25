package com.omgservers.schema.module.runtime.runtimeState;

import com.omgservers.schema.model.runtimeChangeOfState.RuntimeChangeOfStateDto;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRuntimeStateRequest implements ShardedRequest {

    @NotNull
    Long runtimeId;

    @NotNull
    RuntimeChangeOfStateDto runtimeChangeOfState;

    @Override
    public String getRequestShardKey() {
        return runtimeId.toString();
    }
}
