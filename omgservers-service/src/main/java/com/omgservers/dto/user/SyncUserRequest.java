package com.omgservers.dto.user;

import com.omgservers.dto.ShardedRequest;
import com.omgservers.model.event.body.SignUpRequestedEventBodyModel;
import com.omgservers.model.user.UserModel;
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
