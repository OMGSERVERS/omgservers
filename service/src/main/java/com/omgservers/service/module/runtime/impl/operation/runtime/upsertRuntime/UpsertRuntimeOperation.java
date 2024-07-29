package com.omgservers.service.module.runtime.impl.operation.runtime.upsertRuntime;

import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertRuntimeOperation {
    Uni<Boolean> upsertRuntime(ChangeContext<?> changeContext,
                               SqlConnection sqlConnection,
                               int shard,
                               RuntimeModel runtime);
}
