package com.omgservers.schema.module.user;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPlayerRequest implements ShardedRequest {

    @NotNull
    Long userId;

    @NotNull
    Long id;

    @Override
    public String getRequestShardKey() {
        return userId.toString();
    }
}
