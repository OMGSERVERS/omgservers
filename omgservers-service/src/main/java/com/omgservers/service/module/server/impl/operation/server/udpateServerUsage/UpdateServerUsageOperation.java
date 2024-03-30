package com.omgservers.service.module.server.impl.operation.server.udpateServerUsage;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpdateServerUsageOperation {
    Uni<Boolean> updateServerUsage(ChangeContext<?> changeContext,
                                   SqlConnection sqlConnection,
                                   int shard,
                                   Long id,
                                   Integer cpuUsed,
                                   Integer memoryUsed);
}
