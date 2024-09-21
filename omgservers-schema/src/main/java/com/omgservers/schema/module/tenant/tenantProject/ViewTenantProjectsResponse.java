package com.omgservers.schema.module.tenant.tenantProject;

import com.omgservers.schema.model.project.TenantProjectModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewTenantProjectsResponse {

    List<TenantProjectModel> tenantProjects;
}
