package com.omgservers.service.module.matchmaker.impl.operation.deleteMatchmaker;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteMatchmakerOperation {
    Uni<Boolean> deleteMatchmaker(ChangeContext<?> changeContext,
                                  SqlConnection sqlConnection,
                                  int shard,
                                  Long id);
}
