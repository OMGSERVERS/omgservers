package com.omgservers.dto.tenant;

import com.omgservers.dto.ShardedRequest;
import com.omgservers.model.project.ProjectModel;
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
