package com.omgservers.service.operation.server;

import com.omgservers.service.configuration.DatabaseSchemaConfiguration;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
class PrepareShardSqlOperationImpl implements PrepareShardSqlOperation {

    @Override
    public String execute(final String sql,
                          final int shard) {
        final var shardSchema = String.format(DatabaseSchemaConfiguration.SHARD_SCHEMA_FORMAT, shard);
        final var preparedSql = sql.replaceAll("\\$shard", shardSchema);
        return preparedSql;
    }
}
