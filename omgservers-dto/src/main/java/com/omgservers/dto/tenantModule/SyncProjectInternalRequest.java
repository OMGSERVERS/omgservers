package com.omgservers.dto.tenantModule;

import com.omgservers.model.project.ProjectModel;
import com.omgservers.dto.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncProjectInternalRequest implements InternalRequest {

    static public void validate(SyncProjectInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    ProjectModel project;

    @Override
    public String getRequestShardKey() {
        return project.getTenantId().toString();
    }
}
