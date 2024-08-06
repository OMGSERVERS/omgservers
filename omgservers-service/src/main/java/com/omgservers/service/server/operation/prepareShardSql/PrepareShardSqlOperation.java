package com.omgservers.service.server.operation.prepareShardSql;

public interface PrepareShardSqlOperation {
    String prepareShardSql(String sql, int shard);
}
