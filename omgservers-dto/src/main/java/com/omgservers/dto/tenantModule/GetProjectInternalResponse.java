package com.omgservers.dto.tenantModule;

import com.omgservers.model.project.ProjectModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetProjectInternalResponse {

    ProjectModel project;
}
