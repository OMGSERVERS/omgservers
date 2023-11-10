package com.omgservers.service.module.runtime.impl.operation.selectActiveRuntimeGrantsByRuntimeId;

import com.omgservers.model.runtimeGrant.RuntimeGrantModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveRuntimeGrantsByRuntimeIdOperation {
    Uni<List<RuntimeGrantModel>> selectActiveRuntimeGrantsByRuntimeId(SqlConnection sqlConnection,
                                                                      int shard,
                                                                      Long runtimeId);
}
