package com.omgservers.schema.module.user;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.user.UserModel;
import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.user.UserModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncUserRequest implements ShardedRequest {

    @NotNull
    UserModel user;

    @Override
    public String getRequestShardKey() {
        return user.getId().toString();
    }
}
