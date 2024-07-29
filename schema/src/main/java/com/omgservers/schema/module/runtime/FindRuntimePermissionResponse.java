package com.omgservers.schema.module.runtime;

import com.omgservers.schema.model.runtimePermission.RuntimePermissionModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindRuntimePermissionResponse {

    RuntimePermissionModel runtimePermission;
}
