package com.omgservers.service.module.runtime.impl.operation.upsertRuntimeGrant;

import com.omgservers.model.runtimeGrant.RuntimeGrantModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertRuntimeGrantOperation {
    Uni<Boolean> upsertRuntimeGrant(ChangeContext<?> changeContext,
                                    SqlConnection sqlConnection,
                                    int shard,
                                    RuntimeGrantModel runtimeGrant);
}
