package com.omgservers.model.dto.runtime;

import com.omgservers.model.dto.ShardedRequest;
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

    @NotEmpty
    List<Long> ids;

    @Override
    public String getRequestShardKey() {
        return runtimeId.toString();
    }
}
