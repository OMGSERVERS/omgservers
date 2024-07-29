package com.omgservers.service.module.matchmaker.impl.operation.matchmaker.deleteMatchmaker;

import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteMatchmakerOperation {
    Uni<Boolean> deleteMatchmaker(ChangeContext<?> changeContext,
                                  SqlConnection sqlConnection,
                                  int shard,
                                  Long id);
}
