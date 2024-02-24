package com.omgservers.service.module.system.impl.operation.updateEventsStatusByIds;

import com.omgservers.model.event.EventStatusEnum;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface UpdateEventsStatusByIdsOperation {
    Uni<Boolean> updateEventsStatusByIds(ChangeContext<?> changeContext,
                                         SqlConnection sqlConnection,
                                         List<Long> ids,
                                         EventStatusEnum status);
}
