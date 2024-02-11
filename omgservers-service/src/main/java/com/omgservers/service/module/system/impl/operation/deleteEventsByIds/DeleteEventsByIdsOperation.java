package com.omgservers.service.module.system.impl.operation.deleteEventsByIds;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface DeleteEventsByIdsOperation {
    Uni<Boolean> deleteEventsByIds(ChangeContext<?> changeContext,
                                   SqlConnection sqlConnection,
                                   List<Long> ids);
}
