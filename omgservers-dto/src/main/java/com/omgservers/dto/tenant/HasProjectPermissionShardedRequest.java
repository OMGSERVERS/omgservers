package com.omgservers.dto.tenant;

import com.omgservers.model.projectPermission.ProjectPermissionEnum;
import com.omgservers.dto.ShardedRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HasProjectPermissionShardedRequest implements ShardedRequest {

    public static void validate(HasProjectPermissionShardedRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long tenantId;
    Long projectId;
    Long userId;
    ProjectPermissionEnum permission;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
