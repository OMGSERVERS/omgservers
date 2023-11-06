package com.omgservers.service.module.user.impl.operation.selectClient;

import com.omgservers.model.client.ClientModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectClientOperation {
    Uni<ClientModel> selectClient(SqlConnection sqlConnection,
                                  int shard,
                                  Long userId,
                                  Long id,
                                  Boolean deleted);
}
