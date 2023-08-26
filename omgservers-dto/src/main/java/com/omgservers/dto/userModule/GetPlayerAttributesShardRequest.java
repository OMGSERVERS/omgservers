package com.omgservers.dto.userModule;

import com.omgservers.dto.ShardRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPlayerAttributesShardRequest implements ShardRequest {

    static public void validate(GetPlayerAttributesShardRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long userId;
    Long playerId;

    @Override
    public String getRequestShardKey() {
        return userId.toString();
    }
}
