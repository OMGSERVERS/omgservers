package com.omgservers.schema.entrypoint.support;

import com.omgservers.schema.model.projectPermission.ProjectPermissionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteProjectPermissionsSupportResponse {

    List<ProjectPermissionEnum> deletedPermissions;
}
