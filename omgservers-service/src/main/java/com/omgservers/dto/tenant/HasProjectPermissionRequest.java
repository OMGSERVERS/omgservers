package com.omgservers.dto.tenant;

import com.omgservers.dto.ShardedRequest;
import com.omgservers.model.projectPermission.ProjectPermissionEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HasProjectPermissionRequest implements ShardedRequest {

    @NotNull
    Long tenantId;

    @NotNull
    Long projectId;

    @NotNull
    Long userId;

    @NotNull
    ProjectPermissionEnum permission;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
