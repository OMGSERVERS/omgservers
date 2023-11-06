package com.omgservers.service.module.runtime.impl.operation.selectRuntimeGrant;

import com.omgservers.model.runtimeGrant.RuntimeGrantModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectRuntimeGrantOperation {
    Uni<RuntimeGrantModel> selectRuntimeGrant(SqlConnection sqlConnection,
                                              int shard,
                                              Long runtimeId,
                                              Long id,
                                              Boolean deleted);
}
