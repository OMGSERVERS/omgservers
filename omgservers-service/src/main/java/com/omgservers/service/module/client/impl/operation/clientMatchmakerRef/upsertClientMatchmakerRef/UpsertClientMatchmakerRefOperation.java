package com.omgservers.service.module.client.impl.operation.clientMatchmakerRef.upsertClientMatchmakerRef;

import com.omgservers.schema.model.clientMatchmakerRef.ClientMatchmakerRefModel;
import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertClientMatchmakerRefOperation {
    Uni<Boolean> upsertClientMatchmakerRef(ChangeContext<?> changeContext,
                                           SqlConnection sqlConnection,
                                           int shard,
                                           ClientMatchmakerRefModel clientMatchmakerRefModel);
}
