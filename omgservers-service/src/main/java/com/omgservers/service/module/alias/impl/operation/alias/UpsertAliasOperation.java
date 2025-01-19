package com.omgservers.service.module.alias.impl.operation.alias;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertAliasOperation {
    Uni<Boolean> execute(ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         int shard,
                         AliasModel alias);
}
