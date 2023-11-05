package com.omgservers.model.dto.matchmaker;

import com.omgservers.model.dto.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMatchmakerRequest implements ShardedRequest {

    @NotNull
    Long id;

    @NotNull
    Boolean deleted;

    @Override
    public String getRequestShardKey() {
        return id.toString();
    }
}
