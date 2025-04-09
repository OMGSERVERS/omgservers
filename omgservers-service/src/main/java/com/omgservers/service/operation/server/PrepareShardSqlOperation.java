package com.omgservers.service.operation.server;

public interface PrepareShardSqlOperation {
    String execute(String sql, int shard);
}
