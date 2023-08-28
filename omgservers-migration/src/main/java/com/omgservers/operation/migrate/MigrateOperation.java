package com.omgservers.operation.migrate;

public interface MigrateOperation {
    void migrateInternalSchema(String location);

    void migrateShardsSchema(String location);
}
