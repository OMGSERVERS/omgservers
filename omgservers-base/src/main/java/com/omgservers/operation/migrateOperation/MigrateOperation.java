package com.omgservers.operation.migrateOperation;

public interface MigrateOperation {
    void migrateInternalSchema(String location, String table);

    void migrateShardsSchema(String location, String table);
}
