package com.omgservers.dto.runtime;

import com.omgservers.dto.ShardedRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoBroadcastMessageRequest implements ShardedRequest {

    @NotNull
    Long runtimeId;

    @NotBlank
    String message;

    @Override
    public String getRequestShardKey() {
        return runtimeId.toString();
    }
}