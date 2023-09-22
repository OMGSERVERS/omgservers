package com.omgservers.module.migration.impl.operation.migrate;

public interface MigrateOperation {
    void migrateSystemSchema(String location);

    void migrateShardsSchema(String location);
}
