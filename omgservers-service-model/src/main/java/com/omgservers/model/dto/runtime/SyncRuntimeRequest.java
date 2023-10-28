package com.omgservers.model.dto.runtime;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.runtime.RuntimeModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncRuntimeRequest implements ShardedRequest {

    @NotNull
    RuntimeModel runtime;

    @Override
    public String getRequestShardKey() {
        return runtime.getId().toString();
    }
}
