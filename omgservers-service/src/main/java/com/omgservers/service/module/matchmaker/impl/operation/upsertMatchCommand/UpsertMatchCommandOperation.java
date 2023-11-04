package com.omgservers.service.module.matchmaker.impl.operation.upsertMatchCommand;

import com.omgservers.model.matchCommand.MatchCommandModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertMatchCommandOperation {
    Uni<Boolean> upsertMatchCommand(ChangeContext<?> changeContext,
                                    SqlConnection sqlConnection,
                                    int shard,
                                    MatchCommandModel matchCommand);
}
