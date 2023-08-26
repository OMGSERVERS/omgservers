package com.omgservers.base.operation.prepareShardSql;

public interface PrepareShardSqlOperation {
    String prepareShardSql(String sql, int shard);
}
