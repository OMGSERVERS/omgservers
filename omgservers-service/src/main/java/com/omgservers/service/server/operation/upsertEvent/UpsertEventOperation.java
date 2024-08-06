package com.omgservers.service.server.operation.upsertEvent;

import com.omgservers.schema.event.EventModel;
import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertEventOperation {
    Uni<Boolean> upsertEvent(ChangeContext<?> changeContext,
                             SqlConnection sqlConnection,
                             EventModel event);
}
