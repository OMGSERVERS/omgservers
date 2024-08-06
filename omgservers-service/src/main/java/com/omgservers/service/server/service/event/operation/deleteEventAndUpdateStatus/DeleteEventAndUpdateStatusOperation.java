package com.omgservers.service.server.service.event.operation.deleteEventAndUpdateStatus;

import com.omgservers.schema.event.EventStatusEnum;
import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteEventAndUpdateStatusOperation {
    Uni<Boolean> deleteEventAndUpdateStatus(ChangeContext<?> changeContext,
                                            SqlConnection sqlConnection,
                                            Long id,
                                            EventStatusEnum status);
}
