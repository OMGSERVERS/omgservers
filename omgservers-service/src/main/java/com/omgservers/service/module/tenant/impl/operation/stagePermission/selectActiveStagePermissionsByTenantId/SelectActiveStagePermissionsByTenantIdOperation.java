package com.omgservers.service.module.tenant.impl.operation.stagePermission.selectActiveStagePermissionsByTenantId;

import com.omgservers.schema.model.stagePermission.StagePermissionModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveStagePermissionsByTenantIdOperation {
    Uni<List<StagePermissionModel>> selectActiveStagePermissionsByTenantId(SqlConnection sqlConnection,
                                                                           int shard,
                                                                           Long tenantId);
}
