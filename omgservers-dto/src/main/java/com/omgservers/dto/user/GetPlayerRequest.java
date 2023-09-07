package com.omgservers.dto.user;

import com.omgservers.dto.ShardedRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPlayerRequest implements ShardedRequest {

    public static void validate(GetPlayerRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long userId;
    Long stageId;

    @Override
    public String getRequestShardKey() {
        return userId.toString();
    }
}
