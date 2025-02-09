package com.omgservers.service.shard.client.impl.operation.clientMatchmakerRef.selectActiveClientMatchmakerRefsByClientId;

import com.omgservers.schema.model.clientMatchmakerRef.ClientMatchmakerRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveClientMatchmakerRefsByClientIdOperation {
    Uni<List<ClientMatchmakerRefModel>> selectActiveClientMatchmakerRefsByClientId(SqlConnection sqlConnection,
                                                                                   int shard,
                                                                                   Long clientId);
}
