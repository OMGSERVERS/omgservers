package com.omgservers.service.module.tenant.impl.operation.stagePermission.deleteStagePermission;

import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteStagePermissionOperation {
    Uni<Boolean> deleteStagePermission(ChangeContext<?> changeContext,
                                       SqlConnection sqlConnection,
                                       int shard,
                                       Long tenantId,
                                       Long id);
}
