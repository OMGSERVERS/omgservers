package com.omgservers.service.shard.tenant.impl.operation.tenantStageCommand;

import com.omgservers.schema.model.tenantStageCommand.TenantStageCommandModel;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertTenantStageCommandOperation {
    Uni<Boolean> execute(ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         int slot,
                         TenantStageCommandModel tenantStageCommand);
}