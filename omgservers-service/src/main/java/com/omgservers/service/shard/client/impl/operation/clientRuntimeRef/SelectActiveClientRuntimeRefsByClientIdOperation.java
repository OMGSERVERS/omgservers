package com.omgservers.service.shard.client.impl.operation.clientRuntimeRef;

import com.omgservers.schema.model.clientRuntimeRef.ClientRuntimeRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveClientRuntimeRefsByClientIdOperation {
    Uni<List<ClientRuntimeRefModel>> selectActiveClientRuntimeRefsByClientId(SqlConnection sqlConnection,
                                                                             int slot,
                                                                             Long clientId);
}
