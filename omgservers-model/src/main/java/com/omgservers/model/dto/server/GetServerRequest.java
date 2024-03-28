package com.omgservers.model.dto.server;

import com.omgservers.model.dto.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetServerRequest implements ShardedRequest {

    @NotNull
    Long id;

    @Override
    public String getRequestShardKey() {
        return id.toString();
    }
}
