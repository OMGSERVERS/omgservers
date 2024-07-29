package com.omgservers.service.module.runtime.impl.operation.runtimePoolServerContainerRef.selectRuntimePoolServerContainerRef;

import com.omgservers.schema.model.runtimePoolServerContainerRef.RuntimePoolServerContainerRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectRuntimePoolServerContainerRefOperation {
    Uni<RuntimePoolServerContainerRefModel> selectRuntimePoolServerContainerRef(SqlConnection sqlConnection,
                                                                                int shard,
                                                                                Long runtimeId,
                                                                                Long id);
}
