package com.omgservers.service.module.runtime.impl.operation.runtimeServerContainerRef.selectRuntimeServerContainerRefByRuntimeId;

import com.omgservers.model.runtimeServerContainerRef.RuntimeServerContainerRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectRuntimeServerContainerRefByRuntimeIdOperation {
    Uni<RuntimeServerContainerRefModel> selectRuntimeServerContainerRefByRuntimeId(SqlConnection sqlConnection,
                                                                                   int shard,
                                                                                   Long runtimeId);
}
