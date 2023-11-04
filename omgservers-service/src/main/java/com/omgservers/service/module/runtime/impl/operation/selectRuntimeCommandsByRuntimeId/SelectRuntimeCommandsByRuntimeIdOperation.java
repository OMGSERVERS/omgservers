package com.omgservers.service.module.runtime.impl.operation.selectRuntimeCommandsByRuntimeId;

import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectRuntimeCommandsByRuntimeIdOperation {
    Uni<List<RuntimeCommandModel>> selectRuntimeCommandsByRuntimeId(SqlConnection sqlConnection,
                                                                    int shard,
                                                                    Long runtimeId,
                                                                    Boolean deleted);
}
