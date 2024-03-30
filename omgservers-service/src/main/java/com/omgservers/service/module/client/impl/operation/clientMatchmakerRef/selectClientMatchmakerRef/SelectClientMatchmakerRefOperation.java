package com.omgservers.service.module.client.impl.operation.clientMatchmakerRef.selectClientMatchmakerRef;

import com.omgservers.model.clientMatchmakerRef.ClientMatchmakerRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectClientMatchmakerRefOperation {
    Uni<ClientMatchmakerRefModel> selectClientMatchmakerRef(SqlConnection sqlConnection,
                                                            int shard,
                                                            Long clientId,
                                                            Long id);
}
