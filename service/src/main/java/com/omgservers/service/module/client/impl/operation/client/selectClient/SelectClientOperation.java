package com.omgservers.service.module.client.impl.operation.client.selectClient;

import com.omgservers.model.client.ClientModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectClientOperation {
    Uni<ClientModel> selectClient(SqlConnection sqlConnection,
                                  int shard,
                                  Long id);
}
