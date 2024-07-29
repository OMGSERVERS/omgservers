package com.omgservers.service.module.system.impl.operation.event.selectEvent;

import com.omgservers.schema.event.EventModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectEventOperation {
    Uni<EventModel> selectEvent(SqlConnection sqlConnection, Long id);
}
