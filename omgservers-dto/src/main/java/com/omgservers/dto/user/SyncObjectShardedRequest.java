package com.omgservers.dto.user;

import com.omgservers.dto.ShardedRequest;
import com.omgservers.model.object.ObjectModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncObjectShardedRequest implements ShardedRequest {

    public static void validate(SyncObjectShardedRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long userId;
    ObjectModel object;

    @Override
    public String getRequestShardKey() {
        return userId.toString();
    }
}
