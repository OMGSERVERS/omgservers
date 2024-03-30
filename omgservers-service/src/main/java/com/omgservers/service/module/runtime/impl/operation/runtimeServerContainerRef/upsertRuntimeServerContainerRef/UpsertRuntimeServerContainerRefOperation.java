package com.omgservers.service.module.runtime.impl.operation.runtimeServerContainerRef.upsertRuntimeServerContainerRef;

import com.omgservers.model.runtimeServerContainerRef.RuntimeServerContainerRefModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertRuntimeServerContainerRefOperation {
    Uni<Boolean> upsertRuntimeServerContainerRef(ChangeContext<?> changeContext,
                                                 SqlConnection sqlConnection,
                                                 int shard,
                                                 RuntimeServerContainerRefModel runtimeServerContainerRef);
}
