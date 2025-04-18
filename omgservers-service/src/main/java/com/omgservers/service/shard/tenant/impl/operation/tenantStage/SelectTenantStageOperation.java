package com.omgservers.service.shard.tenant.impl.operation.tenantStage;

import com.omgservers.schema.model.tenantStage.TenantStageModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectTenantStageOperation {
    Uni<TenantStageModel> execute(SqlConnection sqlConnection,
                                  int slot,
                                  Long tenantId,
                                  Long id);
}
