package com.omgservers.service.module.tenant.impl.operation.upsertVersionRuntime;

import com.omgservers.model.versionRuntime.VersionRuntimeModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertVersionRuntimeOperation {
    Uni<Boolean> upsertVersionRuntime(ChangeContext<?> changeContext,
                                      SqlConnection sqlConnection,
                                      int shard,
                                      VersionRuntimeModel stageRuntime);
}
