package com.omgservers.service.module.runtime.impl.operation.selectActiveRuntimeGrantsByRuntimeIdAndEntityIds;

import com.omgservers.model.runtimeGrant.RuntimeGrantModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveRuntimeGrantsByRuntimeIdAndEntityIdsOperation {
    Uni<List<RuntimeGrantModel>> selectActiveRuntimeGrantsByRuntimeIdAndEntityIds(SqlConnection sqlConnection,
                                                                                  int shard,
                                                                                  Long runtimeId,
                                                                                  List<Long> entityIds);
}
