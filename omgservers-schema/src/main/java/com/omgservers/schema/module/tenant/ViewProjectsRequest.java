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
public class ViewProjectsRequest implements ShardedRequest {

    @NotNull
    Long tenantId;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
