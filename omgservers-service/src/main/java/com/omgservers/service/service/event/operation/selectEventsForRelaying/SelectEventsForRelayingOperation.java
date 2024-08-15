package com.omgservers.service.service.event.operation.selectEventsForRelaying;

import com.omgservers.service.event.EventProjectionModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectEventsForRelayingOperation {
    Uni<List<EventProjectionModel>> selectEventsForRelaying(SqlConnection sqlConnection,
                                                            int limit);
}
