package com.omgservers.dto.userModule;

import com.omgservers.model.user.UserModel;
import com.omgservers.dto.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncUserInternalRequest implements InternalRequest {

    static public void validate(SyncUserInternalRequest request) {
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
