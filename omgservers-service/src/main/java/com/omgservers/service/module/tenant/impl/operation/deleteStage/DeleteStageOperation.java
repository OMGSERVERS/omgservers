package com.omgservers.service.module.tenant.impl.operation.deleteStage;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteStageOperation {
    Uni<Boolean> deleteStage(ChangeContext<?> changeContext,
                             SqlConnection sqlConnection,
                             int shard,
                             Long tenantId,
                             Long id);
}
