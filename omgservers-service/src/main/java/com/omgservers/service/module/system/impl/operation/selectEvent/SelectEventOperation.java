package com.omgservers.service.module.system.impl.operation.selectEvent;

import com.omgservers.model.event.EventModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectEventOperation {
    Uni<EventModel> selectEvent(SqlConnection sqlConnection, Long id);
}
