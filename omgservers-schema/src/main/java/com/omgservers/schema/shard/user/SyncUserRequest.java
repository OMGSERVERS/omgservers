package com.omgservers.schema.shard.user;

import com.omgservers.schema.shard.ShardRequest;
import com.omgservers.schema.model.user.UserModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncUserRequest implements ShardRequest {

    @NotNull
    UserModel user;

    @Override
    public String getRequestShardKey() {
        return user.getId().toString();
    }
}
