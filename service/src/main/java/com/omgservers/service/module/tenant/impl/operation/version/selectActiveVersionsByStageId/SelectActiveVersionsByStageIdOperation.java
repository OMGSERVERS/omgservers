package com.omgservers.service.module.tenant.impl.operation.version.selectActiveVersionsByStageId;

import com.omgservers.model.version.VersionModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveVersionsByStageIdOperation {
    Uni<List<VersionModel>> selectActiveVersionsByStageId(SqlConnection sqlConnection,
                                                          int shard,
                                                          Long tenantId,
                                                          Long stageId);
}
