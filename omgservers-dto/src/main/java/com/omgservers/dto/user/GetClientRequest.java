package com.omgservers.dto.user;

import com.omgservers.dto.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetClientRequest implements ShardedRequest {

    @NotNull
    Long userId;

    @NotNull
    Long clientId;

    @Override
    public String getRequestShardKey() {
        return userId.toString();
    }
}
