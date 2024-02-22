package com.omgservers.service.module.client.impl.operation.selectActiveClientRuntimeRefsByClientId;

import com.omgservers.model.clientRuntime.ClientRuntimeRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveClientRuntimeRefsByClientIdOperation {
    Uni<List<ClientRuntimeRefModel>> selectActiveClientRuntimeRefsByClientId(SqlConnection sqlConnection,
                                                                             int shard,
                                                                             Long clientId);
}
