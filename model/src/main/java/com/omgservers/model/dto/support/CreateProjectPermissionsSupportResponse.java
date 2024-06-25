package com.omgservers.model.dto.support;

import com.omgservers.model.projectPermission.ProjectPermissionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProjectPermissionsSupportResponse {

    List<ProjectPermissionEnum> createdPermissions;
}
