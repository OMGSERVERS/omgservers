package com.omgservers.model.dto.tenant.versionImageRef;

import com.omgservers.model.dto.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewVersionImageRefsRequest implements ShardedRequest {

    @NotNull
    Long tenantId;

    Long versionId;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
