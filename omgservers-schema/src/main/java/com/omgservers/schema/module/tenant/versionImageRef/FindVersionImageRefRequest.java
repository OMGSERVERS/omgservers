package com.omgservers.schema.module.tenant.versionImageRef;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.versionImageRef.VersionImageRefQualifierEnum;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindVersionImageRefRequest implements ShardedRequest {

    @NotNull
    Long tenantId;

    @NotNull
    Long versionId;

    @NotNull
    VersionImageRefQualifierEnum qualifier;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
