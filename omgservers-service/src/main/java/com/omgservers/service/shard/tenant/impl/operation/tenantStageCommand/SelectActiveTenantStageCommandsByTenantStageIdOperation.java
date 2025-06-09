package com.omgservers.service.shard.tenant.impl.operation.tenantStageCommand;

import com.omgservers.schema.model.tenantStageCommand.TenantStageCommandModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveTenantStageCommandsByTenantStageIdOperation {
    Uni<List<TenantStageCommandModel>> execute(SqlConnection sqlConnection,
                                              int slot,
                                              Long tenantId,
                                              Long tenantStageId);
}