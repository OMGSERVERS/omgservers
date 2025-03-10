package com.omgservers.service.service.event.operation;

import com.omgservers.service.event.EventModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectEventOperation {
    Uni<EventModel> selectEvent(SqlConnection sqlConnection, Long id);
}
