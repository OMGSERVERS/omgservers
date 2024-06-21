package com.omgservers.service.operation.upsertEvent;

import com.omgservers.model.event.EventModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertEventOperation {
    Uni<Boolean> upsertEvent(ChangeContext<?> changeContext,
                             SqlConnection sqlConnection,
                             EventModel event);
}
