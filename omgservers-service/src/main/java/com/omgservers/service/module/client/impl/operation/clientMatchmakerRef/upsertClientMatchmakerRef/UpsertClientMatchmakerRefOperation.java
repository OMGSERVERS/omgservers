package com.omgservers.service.module.client.impl.operation.clientMatchmakerRef.upsertClientMatchmakerRef;

import com.omgservers.model.clientMatchmakerRef.ClientMatchmakerRefModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertClientMatchmakerRefOperation {
    Uni<Boolean> upsertClientMatchmakerRef(ChangeContext<?> changeContext,
                                           SqlConnection sqlConnection,
                                           int shard,
                                           ClientMatchmakerRefModel clientMatchmakerRefModel);
}
