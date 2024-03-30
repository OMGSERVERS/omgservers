package com.omgservers.service.module.tenant.impl.operation.stage.selectActiveStagesByProjectId;

import com.omgservers.model.stage.StageModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveStagesByProjectIdOperation {
    Uni<List<StageModel>> selectActiveStagesByProjectId(SqlConnection sqlConnection,
                                                        int shard,
                                                        Long tenantId,
                                                        Long projectId);
}
