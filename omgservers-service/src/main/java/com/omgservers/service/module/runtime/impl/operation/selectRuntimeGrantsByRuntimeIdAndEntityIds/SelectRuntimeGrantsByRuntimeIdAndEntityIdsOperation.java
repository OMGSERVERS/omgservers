package com.omgservers.service.module.runtime.impl.operation.selectRuntimeGrantsByRuntimeIdAndEntityIds;

import com.omgservers.model.runtimeGrant.RuntimeGrantModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectRuntimeGrantsByRuntimeIdAndEntityIdsOperation {
    Uni<List<RuntimeGrantModel>> selectRuntimeGrantsByRuntimeIdAndEntityIds(SqlConnection sqlConnection,
                                                                            int shard,
                                                                            Long runtimeId,
                                                                            List<Long> entityIds);
}
