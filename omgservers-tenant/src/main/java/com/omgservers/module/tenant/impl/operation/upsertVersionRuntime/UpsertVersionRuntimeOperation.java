package com.omgservers.module.tenant.impl.operation.upsertVersionRuntime;

import com.omgservers.model.versionRuntime.VersionRuntimeModel;
import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertVersionRuntimeOperation {
    Uni<Boolean> upsertStageRuntime(ChangeContext<?> changeContext,
                                    SqlConnection sqlConnection,
                                    int shard,
                                    VersionRuntimeModel stageRuntime);
}
