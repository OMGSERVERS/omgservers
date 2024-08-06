package com.omgservers.service.module.runtime.impl.operation.runtimePoolServerContainerRef.selectRuntimePoolServerContainerRefByRuntimeId;

import com.omgservers.schema.model.runtimePoolServerContainerRef.RuntimePoolServerContainerRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectRuntimePoolServerContainerRefByRuntimeIdOperation {
    Uni<RuntimePoolServerContainerRefModel> selectRuntimePoolServerContainerRefByRuntimeId(SqlConnection sqlConnection,
                                                                                           int shard,
                                                                                           Long runtimeId);
}
