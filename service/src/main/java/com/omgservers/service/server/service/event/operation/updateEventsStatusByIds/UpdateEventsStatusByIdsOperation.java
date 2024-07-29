package com.omgservers.service.server.service.event.operation.updateEventsStatusByIds;

import com.omgservers.schema.event.EventStatusEnum;
import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface UpdateEventsStatusByIdsOperation {
    Uni<Boolean> updateEventsStatusByIds(ChangeContext<?> changeContext,
                                         SqlConnection sqlConnection,
                                         List<Long> ids,
                                         EventStatusEnum status);
}
