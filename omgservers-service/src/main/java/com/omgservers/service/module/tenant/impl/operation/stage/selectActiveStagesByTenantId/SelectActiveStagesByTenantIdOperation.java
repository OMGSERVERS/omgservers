package com.omgservers.service.module.tenant.impl.operation.stage.selectActiveStagesByTenantId;

import com.omgservers.schema.model.stage.StageModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveStagesByTenantIdOperation {
    Uni<List<StageModel>> selectActiveStagesByTenantId(SqlConnection sqlConnection,
                                                       int shard,
                                                       Long tenantId);
}
