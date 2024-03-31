package com.omgservers.model.dto.runtime.serverRuntimeRef;

import com.omgservers.model.dto.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetRuntimeServerContainerRefRequest implements ShardedRequest {

    @NotNull
    Long runtimeId;

    @NotNull
    Long id;

    @Override
    public String getRequestShardKey() {
        return runtimeId.toString();
    }
}