package com.omgservers.module.runtime.impl.operation.selectRuntimeGrantByRuntimeIdAndEntityId;

import com.omgservers.model.runtimeGrant.RuntimeGrantModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectRuntimeGrantByRuntimeIdAndEntityIdOperation {
    Uni<RuntimeGrantModel> selectRuntimeGrantByRuntimeIdAndEntityId(SqlConnection sqlConnection,
                                                                    int shard,
                                                                    Long runtimeId,
                                                                    Long entityId);
}
