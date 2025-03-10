package com.omgservers.service.service.event.operation;

import com.omgservers.service.event.EventModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveEventsOperation {
    Uni<List<EventModel>> execute(SqlConnection sqlConnection, int limit);
}
