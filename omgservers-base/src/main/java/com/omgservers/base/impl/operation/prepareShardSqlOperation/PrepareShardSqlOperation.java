package com.omgservers.base.impl.operation.prepareShardSqlOperation;

public interface PrepareShardSqlOperation {
    String prepareShardSql(String sql, int shard);
}
