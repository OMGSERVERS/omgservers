package com.omgservers.service.module.tenant.impl.operation.stagePermission.hasStagePermission;

import com.omgservers.model.stagePermission.StagePermissionEnum;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface HasStagePermissionOperation {
    Uni<Boolean> hasStagePermission(SqlConnection sqlConnection,
                                    int shard,
                                    Long tenantId,
                                    Long stageId,
                                    Long userId,
                                    StagePermissionEnum stagePermission);
}
