package com.omgservers.service.module.tenant.impl.operation.version.selectVersionIdByStageId;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectVersionIdByStageIdOperation {
    Uni<Long> selectVersionIdByStageId(SqlConnection sqlConnection,
                                       int shard,
                                       Long tenantId,
                                       Long stageId);
}
