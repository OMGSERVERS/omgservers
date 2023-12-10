package com.omgservers.service.module.system.impl.operation.upsertEntity;

import com.omgservers.model.entitiy.EntityModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertEntityOperation {
    Uni<Boolean> upsertEntity(ChangeContext<?> changeContext,
                              SqlConnection sqlConnection,
                              EntityModel entity);
}
