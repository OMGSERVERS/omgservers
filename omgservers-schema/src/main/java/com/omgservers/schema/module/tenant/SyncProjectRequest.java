package com.omgservers.schema.module.tenant;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.project.ProjectModel;
import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.project.ProjectModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncProjectRequest implements ShardedRequest {

    @NotNull
    ProjectModel project;

    @Override
    public String getRequestShardKey() {
        return project.getTenantId().toString();
    }
}
