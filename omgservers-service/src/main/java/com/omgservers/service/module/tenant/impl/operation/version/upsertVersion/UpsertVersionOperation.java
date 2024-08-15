package com.omgservers.service.module.tenant.impl.operation.version.upsertVersion;

import com.omgservers.schema.model.version.VersionModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertVersionOperation {
    Uni<Boolean> upsertVersion(ChangeContext<?> changeContext,
                               SqlConnection sqlConnection,
                               int shard,
                               VersionModel version);
}
