package com.omgservers.service.module.system.impl.operation.event.deleteEventAndUpdateStatus;

import com.omgservers.model.event.EventStatusEnum;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteEventAndUpdateStatusOperation {
    Uni<Boolean> deleteEventAndUpdateStatus(ChangeContext<?> changeContext,
                                            SqlConnection sqlConnection,
                                            Long id,
                                            EventStatusEnum status);
}
