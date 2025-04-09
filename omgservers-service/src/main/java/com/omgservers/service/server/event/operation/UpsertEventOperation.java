package com.omgservers.service.server.event.operation;

import com.omgservers.service.event.EventModel;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertEventOperation {
    Uni<Boolean> upsertEvent(ChangeContext<?> changeContext,
                             SqlConnection sqlConnection,
                             EventModel event);
}
