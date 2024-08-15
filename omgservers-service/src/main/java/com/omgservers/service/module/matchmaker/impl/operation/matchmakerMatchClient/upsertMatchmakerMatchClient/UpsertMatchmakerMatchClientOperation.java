package com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatchClient.upsertMatchmakerMatchClient;

import com.omgservers.schema.model.matchmakerMatchClient.MatchmakerMatchClientModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertMatchmakerMatchClientOperation {
    Uni<Boolean> upsertMatchmakerMatchClient(ChangeContext<?> changeContext,
                                             SqlConnection sqlConnection,
                                             int shard,
                                             MatchmakerMatchClientModel matchmakerMatchClient);
}
