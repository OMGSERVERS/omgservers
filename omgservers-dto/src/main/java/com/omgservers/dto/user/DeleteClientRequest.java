package com.omgservers.dto.user;

import com.omgservers.dto.ShardedRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteClientRequest implements ShardedRequest {

    public static void validate(DeleteClientRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
        // TODO: validate fields
    }

    Long userId;
    Long clientId;

    @Override
    public String getRequestShardKey() {
        return userId.toString();
    }
}
