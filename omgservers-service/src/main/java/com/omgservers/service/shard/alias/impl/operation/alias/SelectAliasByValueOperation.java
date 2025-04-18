package com.omgservers.service.shard.alias.impl.operation.alias;

import com.omgservers.schema.model.alias.AliasModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectAliasByValueOperation {
    Uni<AliasModel> execute(SqlConnection sqlConnection,
                            int slot,
                            Long shardKey,
                            Long uniquenessGroup,
                            String value);
}
