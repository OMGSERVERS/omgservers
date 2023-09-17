package com.omgservers.dto.runtime;

import com.omgservers.dto.ShardedRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoMulticastMessageRequest implements ShardedRequest {

    @NotNull
    Long runtimeId;

    @NotNull
    List<Long> clientIds;

    @NotBlank
    String message;

    @Override
    public String getRequestShardKey() {
        return runtimeId.toString();
    }
}
