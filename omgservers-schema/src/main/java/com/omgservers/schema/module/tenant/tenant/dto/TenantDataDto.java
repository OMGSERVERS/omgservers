package com.omgservers.schema.module.tenant.tenant.dto;

import com.omgservers.schema.model.project.ProjectModel;
import com.omgservers.schema.model.projectPermission.ProjectPermissionModel;
import com.omgservers.schema.model.stage.StageModel;
import com.omgservers.schema.model.stagePermission.StagePermissionModel;
import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.schema.model.tenantPermission.TenantPermissionModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantDataDto {

    @NotNull
    TenantModel tenant;

    @NotNull
    List<TenantPermissionModel> tenantPermissions;

    @NotNull
    List<ProjectModel> projects;

    @NotNull
    List<ProjectPermissionModel> projectPermissions;

    @NotNull
    List<StageModel> stages;

    @NotNull
    List<StagePermissionModel> stagePermissions;
}
