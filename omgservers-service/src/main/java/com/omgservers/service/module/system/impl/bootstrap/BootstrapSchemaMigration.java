package com.omgservers.service.module.system.impl.bootstrap;

import com.omgservers.service.ServiceConfiguration;
import com.omgservers.service.module.system.impl.operation.migrateSchema.MigrateSchemaOperation;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.quarkus.runtime.StartupEvent;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class BootstrapSchemaMigration {

    final GetConfigOperation getConfigOperation;
    final MigrateSchemaOperation migrateSchemaOperation;

    @WithSpan
    void startup(@Observes @Priority(ServiceConfiguration.START_UP_SCHEMA_MIGRATION_PRIORITY) StartupEvent event) {
        if (getConfigOperation.getServiceConfig().disableMigration()) {
            log.warn("Schema migration was disabled, skip operation");
        } else {
            migrateSchemaOperation.migrateSystemSchema("db/system");
            migrateSchemaOperation.migrateShardsSchema("db/shards");
        }
    }
}
