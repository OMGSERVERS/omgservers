package com.omgservers.dto.user;

import com.omgservers.dto.ShardedRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeletePlayerShardedRequest implements ShardedRequest {

    static public void validate(DeletePlayerShardedRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long userId;
    Long id;

    @Override
    public String getRequestShardKey() {
        return userId.toString();
    }
}
