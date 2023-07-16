package com.omgservers.application.module.tenantModule.impl.service.projectInternalService.response;

import com.omgservers.application.module.tenantModule.model.project.ProjectModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetProjectInternalResponse {

    ProjectModel project;
}
