package com.omgservers.module.tenant.impl.service.stageShardedService.impl.method.hasStagePermission;

import com.omgservers.dto.tenant.HasStagePermissionShardedRequest;
import com.omgservers.dto.tenant.HasStagePermissionShardedResponse;
import io.smallrye.mutiny.Uni;

public interface HasStagePermissionMethod {
    Uni<HasStagePermissionShardedResponse> hasStagePermission(HasStagePermissionShardedRequest request);
}
