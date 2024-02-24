package com.omgservers.service.module.system.impl.operation.selectEventsForRelaying;

import com.omgservers.model.event.EventProjectionModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectEventsForRelayingOperation {
    Uni<List<EventProjectionModel>> selectEventsForRelaying(SqlConnection sqlConnection,
                                                            int limit);
}
