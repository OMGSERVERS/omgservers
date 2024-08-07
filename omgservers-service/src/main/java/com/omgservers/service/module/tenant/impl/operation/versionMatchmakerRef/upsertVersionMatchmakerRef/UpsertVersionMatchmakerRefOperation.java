package com.omgservers.service.module.tenant.impl.operation.versionMatchmakerRef.upsertVersionMatchmakerRef;

import com.omgservers.schema.model.versionMatchmakerRef.VersionMatchmakerRefModel;
import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertVersionMatchmakerRefOperation {
    Uni<Boolean> upsertVersionMatchmakerRef(ChangeContext<?> changeContext,
                                            SqlConnection sqlConnection,
                                            int shard,
                                            VersionMatchmakerRefModel versionMatchmakerRef);
}
