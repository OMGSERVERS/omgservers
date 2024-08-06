package com.omgservers.service.module.tenant.impl.operation.stagePermission.selectActiveStagePermissionsByStageId;

import com.omgservers.schema.model.stagePermission.StagePermissionModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveStagePermissionsByStageIdOperation {
    Uni<List<StagePermissionModel>> selectActiveStagePermissionsByStageId(SqlConnection sqlConnection,
                                                                          int shard,
                                                                          Long tenantId,
                                                                          Long stageId);
}
