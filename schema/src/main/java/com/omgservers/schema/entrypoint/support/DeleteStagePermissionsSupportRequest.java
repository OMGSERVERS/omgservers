package com.omgservers.schema.entrypoint.support;

import com.omgservers.schema.model.stagePermission.StagePermissionEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteStagePermissionsSupportRequest {

    @NotNull
    Long tenantId;

    @NotNull
    Long stageId;

    @NotNull
    Long userId;

    @NotEmpty
    Set<StagePermissionEnum> permissionsToDelete;
}
