package com.omgservers.service.operation.server;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
class PrepareShardSqlOperationImpl implements PrepareShardSqlOperation {

    @Override
    public String prepareShardSql(final String sql,
                                  final int shard) {
        final var schemaName = String.format("shard_%05d", shard);
        final var preparedSql = sql.replaceAll("\\$schema", schemaName);
        return preparedSql;
    }
}
