package com.omgservers.dto.userModule;

import com.omgservers.dto.ShardRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetClientShardRequest implements ShardRequest {

    static public void validate(GetClientShardRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long userId;
    Long clientId;

    @Override
    public String getRequestShardKey() {
        return userId.toString();
    }
}
