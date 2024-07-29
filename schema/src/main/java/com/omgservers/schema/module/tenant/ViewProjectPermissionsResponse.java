package com.omgservers.schema.module.tenant;

import com.omgservers.schema.model.projectPermission.ProjectPermissionModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewProjectPermissionsResponse {

    List<ProjectPermissionModel> projectPermissions;
}
