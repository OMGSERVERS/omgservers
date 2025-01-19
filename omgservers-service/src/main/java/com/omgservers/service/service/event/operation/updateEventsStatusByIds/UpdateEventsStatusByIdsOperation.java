package com.omgservers.service.service.event.operation.updateEventsStatusByIds;

import com.omgservers.service.event.EventStatusEnum;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface UpdateEventsStatusByIdsOperation {
    Uni<Boolean> updateEventsStatusByIds(ChangeContext<?> changeContext,
                                         SqlConnection sqlConnection,
                                         List<Long> ids,
                                         EventStatusEnum status);
}
