package com.omgservers.service.module.tenant.impl.operation.selectStage;

import com.omgservers.model.stage.StageModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectStageOperation {
    Uni<StageModel> selectStage(SqlConnection sqlConnection,
                                int shard,
                                Long tenantId,
                                Long id);
}
