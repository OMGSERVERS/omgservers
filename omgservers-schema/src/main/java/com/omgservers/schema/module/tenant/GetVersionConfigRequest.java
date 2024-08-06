package com.omgservers.schema.module.tenant;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetVersionConfigRequest implements ShardedRequest {

    @NotNull
    Long tenantId;

    @NotNull
    Long versionId;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
