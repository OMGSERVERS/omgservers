package com.omgservers.service.shard.client.impl.operation.client;

import com.omgservers.schema.model.client.ClientModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectClientOperation {
    Uni<ClientModel> selectClient(SqlConnection sqlConnection,
                                  int shard,
                                  Long id);
}
