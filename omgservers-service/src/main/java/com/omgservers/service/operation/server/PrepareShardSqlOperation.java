package com.omgservers.service.operation.server;

public interface PrepareShardSqlOperation {
    String prepareShardSql(String sql, int shard);
}
