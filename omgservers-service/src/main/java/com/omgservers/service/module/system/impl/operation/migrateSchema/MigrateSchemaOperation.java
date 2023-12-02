package com.omgservers.service.module.system.impl.operation.migrateSchema;

public interface MigrateSchemaOperation {
    void migrateSystemSchema(String location);

    void migrateShardsSchema(String location);
}
