package com.omgservers.schema.module.tenant;

import com.omgservers.schema.model.project.ProjectModel;
import com.omgservers.schema.model.project.ProjectModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetProjectResponse {

    ProjectModel project;
}
