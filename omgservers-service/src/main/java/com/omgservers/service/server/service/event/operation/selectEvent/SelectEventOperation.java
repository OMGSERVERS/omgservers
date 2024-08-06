package com.omgservers.service.server.service.event.operation.selectEvent;

import com.omgservers.schema.event.EventModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectEventOperation {
    Uni<EventModel> selectEvent(SqlConnection sqlConnection, Long id);
}
