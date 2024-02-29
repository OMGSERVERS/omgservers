package com.omgservers.model.dto.runtime;

import com.omgservers.model.dto.ShardedRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoStopMatchmakingRequest implements ShardedRequest {

    @NotNull
    Long runtimeId;

    @NotBlank
    String reason;

    @Override
    public String getRequestShardKey() {
        return runtimeId.toString();
    }
}
