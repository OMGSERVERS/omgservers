package com.omgservers.model.dto.user;

import com.omgservers.model.dto.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteClientRequest implements ShardedRequest {

    @NotNull
    Long userId;

    @NotNull
    Long clientId;

    @Override
    public String getRequestShardKey() {
        return userId.toString();
    }
}
