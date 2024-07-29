package com.omgservers.service.module.tenant.impl.operation.project.deleteProject;

import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteProjectOperation {
    Uni<Boolean> deleteProject(ChangeContext<?> changeContext,
                               SqlConnection sqlConnection,
                               int shard,
                               Long tenantId,
                               Long id);
}
