package com.omgservers.module.matchmaker.impl.operation.upsertMatchCommand;

import com.omgservers.model.matchCommand.MatchCommandModel;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertMatchCommandOperation {
    Uni<Boolean> upsertMatchCommand(ChangeContext<?> changeContext,
                                    SqlConnection sqlConnection,
                                    int shard,
                                    MatchCommandModel matchCommand);
}
