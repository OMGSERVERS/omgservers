package com.omgservers.service.module.system.impl.operation.selectEventsForUpdate;

import com.omgservers.model.event.EventModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectEventsForUpdateOperation {
    Uni<List<EventModel>> selectEventsForUpdate(SqlConnection sqlConnection,
                                                int limit);
}
