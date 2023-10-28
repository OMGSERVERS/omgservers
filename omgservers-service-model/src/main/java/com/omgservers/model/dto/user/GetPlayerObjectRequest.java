package com.omgservers.model.dto.user;

import com.omgservers.model.dto.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPlayerObjectRequest implements ShardedRequest {

    @NotNull
    Long userId;

    @NotNull
    Long playerId;

    @Override
    public String getRequestShardKey() {
        return userId.toString();
    }
}
