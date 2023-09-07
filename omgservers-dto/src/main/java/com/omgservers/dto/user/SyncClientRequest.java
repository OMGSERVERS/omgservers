package com.omgservers.dto.user;

import com.omgservers.dto.ShardedRequest;
import com.omgservers.model.client.ClientModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncClientRequest implements ShardedRequest {

    public static void validate(SyncClientRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    ClientModel client;

    @Override
    public String getRequestShardKey() {
        return client.getUserId().toString();
    }
}
