package com.omgservers.dto.tenantModule;

import com.omgservers.dto.ShardRequest;
import com.omgservers.model.project.ProjectModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncProjectShardRequest implements ShardRequest {

    static public void validate(SyncProjectShardRequest request) {
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
