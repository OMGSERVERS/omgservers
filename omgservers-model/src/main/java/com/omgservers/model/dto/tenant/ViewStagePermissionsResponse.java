package com.omgservers.model.dto.tenant;

import com.omgservers.model.stagePermission.StagePermissionModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewStagePermissionsResponse {

    List<StagePermissionModel> stagePermissions;
}
