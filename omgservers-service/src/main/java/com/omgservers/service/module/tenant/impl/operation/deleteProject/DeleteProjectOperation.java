package com.omgservers.service.module.tenant.impl.operation.deleteProject;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteProjectOperation {
    Uni<Boolean> deleteProject(ChangeContext<?> changeContext,
                               SqlConnection sqlConnection,
                               int shard,
                               Long tenantId,
                               Long id);
}
