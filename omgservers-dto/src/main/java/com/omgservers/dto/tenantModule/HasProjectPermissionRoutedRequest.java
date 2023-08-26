package com.omgservers.dto.tenantModule;

import com.omgservers.model.projectPermission.ProjectPermissionEnum;
import com.omgservers.dto.RoutedRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HasProjectPermissionRoutedRequest implements RoutedRequest {

    static public void validate(HasProjectPermissionRoutedRequest request) {
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
