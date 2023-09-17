package com.omgservers.dto.runtime;

import com.omgservers.dto.ShardedRequest;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeGrant.RuntimeGrantModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncRuntimeGrantRequest implements ShardedRequest {

    @NotNull
    RuntimeGrantModel runtimeGrant;

    @Override
    public String getRequestShardKey() {
        return runtimeGrant.getRuntimeId().toString();
    }
}
