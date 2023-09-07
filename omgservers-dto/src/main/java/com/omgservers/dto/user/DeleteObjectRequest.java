package com.omgservers.dto.user;

import com.omgservers.dto.ShardedRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteObjectRequest implements ShardedRequest {

    public static void validate(DeleteObjectRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long userId;
    Long playerId;
    Long id;

    @Override
    public String getRequestShardKey() {
        return userId.toString();
    }
}
