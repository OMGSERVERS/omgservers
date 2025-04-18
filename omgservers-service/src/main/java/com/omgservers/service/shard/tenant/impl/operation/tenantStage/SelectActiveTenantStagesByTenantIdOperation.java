package com.omgservers.service.shard.tenant.impl.operation.tenantStage;

import com.omgservers.schema.model.tenantStage.TenantStageModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveTenantStagesByTenantIdOperation {
    Uni<List<TenantStageModel>> execute(SqlConnection sqlConnection,
                                        int slot,
                                        Long tenantId);
}
