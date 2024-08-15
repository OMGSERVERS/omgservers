package com.omgservers.service.operation.prepareShardSql;

public interface PrepareShardSqlOperation {
    String prepareShardSql(String sql, int shard);
}
