package com.omgservers.service.module.tenant.impl.operation.selectStagesByProjectId;

import com.omgservers.model.stage.StageModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectStagesByProjectIdOperation {
    Uni<List<StageModel>> selectStagesByProjectId(SqlConnection sqlConnection,
                                                  int shard,
                                                  Long tenantId,
                                                  Long projectId,
                                                  Boolean deleted);
}
