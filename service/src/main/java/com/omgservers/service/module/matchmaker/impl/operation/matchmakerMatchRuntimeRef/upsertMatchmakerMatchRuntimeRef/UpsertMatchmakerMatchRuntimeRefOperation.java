package com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatchRuntimeRef.upsertMatchmakerMatchRuntimeRef;

import com.omgservers.model.matchmakerMatchRuntimeRef.MatchmakerMatchRuntimeRefModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertMatchmakerMatchRuntimeRefOperation {
    Uni<Boolean> upsertMatchmakerMatchRuntimeRef(ChangeContext<?> changeContext,
                                                 SqlConnection sqlConnection,
                                                 int shard,
                                                 MatchmakerMatchRuntimeRefModel matchmakerMatchRuntimeRef);
}
