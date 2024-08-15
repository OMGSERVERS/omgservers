package com.omgservers.service.service.event.operation.deleteEventAndUpdateStatus;

import com.omgservers.service.event.EventStatusEnum;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteEventAndUpdateStatusOperation {
    Uni<Boolean> deleteEventAndUpdateStatus(ChangeContext<?> changeContext,
                                            SqlConnection sqlConnection,
                                            Long id,
                                            EventStatusEnum status);
}
