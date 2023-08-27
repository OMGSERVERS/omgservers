package com.omgservers.operation.migrateOperation;

public interface MigrateOperation {
    void migrateInternalSchema(String location);

    void migrateShardsSchema(String location);
}
