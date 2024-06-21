package com.omgservers.model.dto.tenant;

import com.omgservers.model.dto.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetVersionLobbyRequestRequest implements ShardedRequest {

    @NotNull
    Long tenantId;

    @NotNull
    Long id;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
