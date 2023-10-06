package com.omgservers.dto.runtime;

import com.omgservers.dto.ShardedRequest;
import com.omgservers.model.runtimeCommand.RuntimeCommandStatusEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRuntimeCommandsStatusRequest implements ShardedRequest {

    @NotNull
    Long runtimeId;

    @NotNull
    @NotEmpty
    List<Long> ids;

    @NotNull
    RuntimeCommandStatusEnum status;

    @Override
    public String getRequestShardKey() {
        return runtimeId.toString();
    }
}
