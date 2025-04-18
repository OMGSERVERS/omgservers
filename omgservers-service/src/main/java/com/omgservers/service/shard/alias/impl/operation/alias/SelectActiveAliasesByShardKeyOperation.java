package com.omgservers.service.shard.alias.impl.operation.alias;

import com.omgservers.schema.model.alias.AliasModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveAliasesByShardKeyOperation {
    Uni<List<AliasModel>> execute(SqlConnection sqlConnection,
                                  int slot,
                                  Long shardKey);
}
