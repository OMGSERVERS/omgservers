package com.omgservers.service.module.tenant.impl.operation.version.selectActiveVersionProjectionsByStageId;

import com.omgservers.schema.model.version.VersionProjectionModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveVersionProjectionsByStageIdOperation {
    Uni<List<VersionProjectionModel>> selectActiveVersionProjectionsByStageId(SqlConnection sqlConnection,
                                                                              int shard,
                                                                              Long tenantId,
                                                                              Long stageId);
}
