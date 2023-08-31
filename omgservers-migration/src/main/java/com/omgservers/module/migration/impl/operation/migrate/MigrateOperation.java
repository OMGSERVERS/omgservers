package com.omgservers.module.migration.impl.operation.migrate;

public interface MigrateOperation {
    void migrateInternalSchema(String location);

    void migrateShardsSchema(String location);
}
