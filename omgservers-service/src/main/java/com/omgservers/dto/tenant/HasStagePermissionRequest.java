package com.omgservers.dto.tenant;

import com.omgservers.dto.ShardedRequest;
import com.omgservers.model.stagePermission.StagePermissionEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HasStagePermissionRequest implements ShardedRequest {

    @NotNull
    Long tenantId;

    @NotNull
    Long stageId;

    @NotNull
    Long userId;

    @NotNull
    StagePermissionEnum permission;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
