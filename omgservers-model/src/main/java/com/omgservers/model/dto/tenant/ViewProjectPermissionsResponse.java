package com.omgservers.model.dto.tenant;

import com.omgservers.model.projectPermission.ProjectPermissionModel;
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
