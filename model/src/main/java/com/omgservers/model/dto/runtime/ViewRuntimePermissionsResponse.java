package com.omgservers.model.dto.runtime;

import com.omgservers.model.runtimePermission.RuntimePermissionModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewRuntimePermissionsResponse {

    List<RuntimePermissionModel> runtimePermissions;
}
