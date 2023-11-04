package com.omgservers.model.dto.matchmaker;

import com.omgservers.model.dto.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindMatchClientRequest implements ShardedRequest {

    @NotNull
    Long matchmakerId;

    @NotNull
    Long clientId;

    @Override
    public String getRequestShardKey() {
        return matchmakerId.toString();
    }
}
