package com.omgservers.dto.userModule;

import com.omgservers.dto.ShardRequest;
import com.omgservers.model.client.ClientModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncClientShardRequest implements ShardRequest {

    static public void validate(SyncClientShardRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long userId;
    ClientModel client;

    @Override
    public String getRequestShardKey() {
        return userId.toString();
    }
}
