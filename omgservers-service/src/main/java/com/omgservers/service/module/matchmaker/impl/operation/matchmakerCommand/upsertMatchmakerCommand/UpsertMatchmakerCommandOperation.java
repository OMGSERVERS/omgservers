package com.omgservers.service.module.matchmaker.impl.operation.matchmakerCommand.upsertMatchmakerCommand;

import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertMatchmakerCommandOperation {
    Uni<Boolean> upsertMatchmakerCommand(ChangeContext<?> changeContext,
                                         SqlConnection sqlConnection,
                                         int shard,
                                         MatchmakerCommandModel matchmakerCommand);
}
