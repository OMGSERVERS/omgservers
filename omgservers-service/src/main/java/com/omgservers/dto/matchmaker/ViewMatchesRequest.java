package com.omgservers.dto.matchmaker;

import com.omgservers.dto.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewMatchesRequest implements ShardedRequest {

    @NotNull
    Long matchmakerId;

    @NotNull
    Boolean deleted;

    @Override
    public String getRequestShardKey() {
        return matchmakerId.toString();
    }
}
