package com.omgservers.application.operation.prepareShardSqlOperation;

public interface PrepareShardSqlOperation {
    String prepareShardSql(String sql, int shard);
}
