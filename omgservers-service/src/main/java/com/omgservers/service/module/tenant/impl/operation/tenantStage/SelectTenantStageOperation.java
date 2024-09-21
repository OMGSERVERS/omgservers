package com.omgservers.service.module.tenant.impl.operation.tenantStage;

import com.omgservers.schema.model.tenantStage.TenantStageModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectTenantStageOperation {
    Uni<TenantStageModel> execute(SqlConnection sqlConnection,
                                  int shard,
                                  Long tenantId,
                                  Long id);
}
