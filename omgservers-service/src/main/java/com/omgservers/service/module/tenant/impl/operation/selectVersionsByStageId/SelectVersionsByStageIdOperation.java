package com.omgservers.service.module.tenant.impl.operation.selectVersionsByStageId;

import com.omgservers.model.version.VersionModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectVersionsByStageIdOperation {
    Uni<List<VersionModel>> selectVersionsByStageId(SqlConnection sqlConnection,
                                                    int shard,
                                                    Long tenantId,
                                                    Long stageId,
                                                    Boolean deleted);
}
