package com.omgservers.service.module.runtime.impl.operation.runtimeServerContainerRef.selectRuntimeServerContainerRef;

import com.omgservers.model.runtimeServerContainerRef.RuntimeServerContainerRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectRuntimeServerContainerRefOperation {
    Uni<RuntimeServerContainerRefModel> selectRuntimeServerContainerRef(SqlConnection sqlConnection,
                                                                        int shard,
                                                                        Long runtimeId,
                                                                        Long id);
}
