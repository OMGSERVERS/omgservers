package com.omgservers.model.dto.user;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.object.ObjectModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncObjectRequest implements ShardedRequest {

    @NotNull
    Long userId;

    @NotNull
    ObjectModel object;

    @Override
    public String getRequestShardKey() {
        return userId.toString();
    }
}
