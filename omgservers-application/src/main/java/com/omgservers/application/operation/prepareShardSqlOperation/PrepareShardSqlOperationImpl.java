package com.omgservers.application.operation.prepareShardSqlOperation;

import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
class PrepareShardSqlOperationImpl implements PrepareShardSqlOperation {

    @Override
    public String prepareShardSql(String sql, int shard) {
        final var schemaName = String.format("shard_%05d", shard);
        final var preparedSql = sql.replaceAll("\\$schema", schemaName);
        return preparedSql;
    }
}
