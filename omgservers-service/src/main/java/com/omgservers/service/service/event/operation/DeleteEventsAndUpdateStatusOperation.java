package com.omgservers.service.service.event.operation;

import com.omgservers.service.event.EventStatusEnum;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface DeleteEventsAndUpdateStatusOperation {
    Uni<Boolean> execute(ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         List<Long> ids,
                         EventStatusEnum status);
}
