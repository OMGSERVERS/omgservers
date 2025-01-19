package com.omgservers.service.operation.server;

import com.omgservers.service.event.EventModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertEventOperation {
    Uni<Boolean> upsertEvent(ChangeContext<?> changeContext,
                             SqlConnection sqlConnection,
                             EventModel event);
}
