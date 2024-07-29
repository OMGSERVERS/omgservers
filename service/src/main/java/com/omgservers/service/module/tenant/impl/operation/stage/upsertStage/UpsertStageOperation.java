package com.omgservers.service.module.tenant.impl.operation.stage.upsertStage;

import com.omgservers.schema.model.stage.StageModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertStageOperation {
    Uni<Boolean> upsertStage(ChangeContext<?> changeContext,
                             SqlConnection sqlConnection,
                             int shard,
                             StageModel stage);
}
