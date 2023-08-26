package com.omgservers.dto.tenantModule;

import com.omgservers.model.projectPermission.ProjectPermissionEnum;
import com.omgservers.dto.ShardRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HasProjectPermissionShardRequest implements ShardRequest {

    static public void validate(HasProjectPermissionShardRequest request) {
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
