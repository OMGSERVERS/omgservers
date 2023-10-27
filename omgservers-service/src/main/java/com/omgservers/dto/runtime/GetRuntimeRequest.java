package com.omgservers.dto.runtime;

import com.omgservers.dto.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetRuntimeRequest implements ShardedRequest {

    @NotNull
    Long id;

    @NotNull
    Boolean deleted;

    @Override
    public String getRequestShardKey() {
        return id.toString();
    }
}
