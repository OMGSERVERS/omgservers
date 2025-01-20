package com.omgservers.service.shard.client.impl.operation.clientRuntimeRef.selectClientRuntimeRefByClientIdAndRuntimeId;

import com.omgservers.schema.model.clientRuntimeRef.ClientRuntimeRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectClientRuntimeRefByClientIdAndRuntimeIdOperation {
    Uni<ClientRuntimeRefModel> selectClientRuntimeRefByClientIdAndRuntimeId(SqlConnection sqlConnection,
                                                                            int shard,
                                                                            Long clientId,
                                                                            Long runtimeId);
}
