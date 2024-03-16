package com.omgservers.service.module.matchmaker.impl.operation.upsertMatchmakerMatchCommand;

import com.omgservers.model.matchCommand.MatchmakerMatchCommandModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertMatchmakerMatchCommandOperation {
    Uni<Boolean> upsertMatchmakerMatchCommand(ChangeContext<?> changeContext,
                                              SqlConnection sqlConnection,
                                              int shard,
                                              MatchmakerMatchCommandModel matchmakerMatchCommand);
}
