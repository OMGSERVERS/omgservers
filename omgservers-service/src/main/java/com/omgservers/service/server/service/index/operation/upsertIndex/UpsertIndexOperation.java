package com.omgservers.service.server.service.index.operation.upsertIndex;

import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import com.omgservers.schema.model.index.IndexModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertIndexOperation {
    Uni<Boolean> upsertIndex(ChangeContext<?> changeContext,
                             SqlConnection sqlConnection,
                             IndexModel index);
}
