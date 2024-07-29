package com.omgservers.schema.module.runtime;

import com.omgservers.schema.model.runtimePermission.RuntimePermissionModel;
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
