package com.omgservers.dto.runtime;

import com.omgservers.dto.ShardedRequest;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteRuntimeCommandsRequest implements ShardedRequest {

    @NotNull
    Long runtimeId;

    @NotNull
    @NotEmpty
    List<Long> ids;

    @Override
    public String getRequestShardKey() {
        return runtimeId.toString();
    }
}
