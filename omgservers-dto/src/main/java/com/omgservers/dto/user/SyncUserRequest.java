package com.omgservers.dto.user;

import com.omgservers.dto.ShardedRequest;
import com.omgservers.model.user.UserModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncUserRequest implements ShardedRequest {

    public static void validate(SyncUserRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    UserModel user;

    @Override
    public String getRequestShardKey() {
        return user.getId().toString();
    }
}