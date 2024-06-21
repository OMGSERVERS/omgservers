package com.omgservers.service.module.tenant.impl.operation.project.upsertProject;

import com.omgservers.model.project.ProjectModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertProjectOperation {
    Uni<Boolean> upsertProject(ChangeContext<?> changeContext,
                               SqlConnection sqlConnection,
                               int shard,
                               ProjectModel project);
}
