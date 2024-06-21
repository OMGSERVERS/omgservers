package com.omgservers.service.module.system.impl.operation.index.upsertIndex;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.model.index.IndexModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertIndexOperation {
    Uni<Boolean> upsertIndex(ChangeContext<?> changeContext,
                             SqlConnection sqlConnection,
                             IndexModel index);
}
