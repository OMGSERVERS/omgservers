package com.omgservers.service.module.matchmaker.impl.operation.upsertMatchRuntimeRef;

import com.omgservers.model.matchRuntimeRef.MatchRuntimeRefModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertMatchRuntimeRefOperation {
    Uni<Boolean> upsertMatchRuntimeRef(ChangeContext<?> changeContext,
                                       SqlConnection sqlConnection,
                                       int shard,
                                       MatchRuntimeRefModel matchRuntimeRef);
}
