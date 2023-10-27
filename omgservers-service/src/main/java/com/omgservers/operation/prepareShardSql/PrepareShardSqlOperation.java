package com.omgservers.operation.prepareShardSql;

public interface PrepareShardSqlOperation {
    String prepareShardSql(String sql, int shard);
}
