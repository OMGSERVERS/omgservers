package com.omgservers.service.module.tenant.impl.operation.stagePermission.upsertStagePermission;

import com.omgservers.model.stagePermission.StagePermissionModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertStagePermissionOperation {
    Uni<Boolean> upsertStagePermission(ChangeContext<?> changeContext,
                                       SqlConnection sqlConnection,
                                       int shard,
                                       StagePermissionModel stagePermission);
}
