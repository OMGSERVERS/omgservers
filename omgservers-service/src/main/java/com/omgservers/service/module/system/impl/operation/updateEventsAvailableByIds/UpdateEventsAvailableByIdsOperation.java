package com.omgservers.service.module.system.impl.operation.updateEventsAvailableByIds;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface UpdateEventsAvailableByIdsOperation {
    Uni<Boolean> updateEventsAvailableByIds(ChangeContext<?> changeContext,
                                            SqlConnection sqlConnection,
                                            List<Long> ids);
}
