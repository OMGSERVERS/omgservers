package com.omgservers.application.module.tenantModule.impl.service.projectInternalService.request;

import com.omgservers.application.module.tenantModule.model.project.ProjectModel;
import com.omgservers.application.module.tenantModule.model.stage.StageModel;
import com.omgservers.application.request.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProjectInternalRequest implements InternalRequest {

    static public void validate(CreateProjectInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    ProjectModel project;
    StageModel stage;

    @Override
    public String getRequestShardKey() {
        return project.getTenant().toString();
    }
}
