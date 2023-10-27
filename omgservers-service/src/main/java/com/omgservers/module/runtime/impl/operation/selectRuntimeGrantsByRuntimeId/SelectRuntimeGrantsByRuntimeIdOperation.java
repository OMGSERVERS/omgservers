package com.omgservers.module.runtime.impl.operation.selectRuntimeGrantsByRuntimeId;

import com.omgservers.model.runtimeGrant.RuntimeGrantModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectRuntimeGrantsByRuntimeIdOperation {
    Uni<List<RuntimeGrantModel>> selectRuntimeGrantsByRuntimeId(SqlConnection sqlConnection,
                                                                int shard,
                                                                Long runtimeId);
}
