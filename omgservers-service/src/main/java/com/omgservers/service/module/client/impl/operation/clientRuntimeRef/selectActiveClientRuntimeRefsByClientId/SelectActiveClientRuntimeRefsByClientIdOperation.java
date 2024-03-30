package com.omgservers.service.module.client.impl.operation.clientRuntimeRef.selectActiveClientRuntimeRefsByClientId;

import com.omgservers.model.clientRuntimeRef.ClientRuntimeRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveClientRuntimeRefsByClientIdOperation {
    Uni<List<ClientRuntimeRefModel>> selectActiveClientRuntimeRefsByClientId(SqlConnection sqlConnection,
                                                                             int shard,
                                                                             Long clientId);
}
