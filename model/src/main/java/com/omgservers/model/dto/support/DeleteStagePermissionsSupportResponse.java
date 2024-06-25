package com.omgservers.model.dto.support;

import com.omgservers.model.stagePermission.StagePermissionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteStagePermissionsSupportResponse {

    List<StagePermissionEnum> deletedPermissions;
}
