package com.omgservers.service.module.client.impl.operation.selectActiveClientRuntimesByClientId;

import com.omgservers.model.clientRuntime.ClientRuntimeModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveClientRuntimesByClientIdOperation {
    Uni<List<ClientRuntimeModel>> selectActiveClientRuntimesByClientId(SqlConnection sqlConnection,
                                                                       int shard,
                                                                       Long clientId);
}
