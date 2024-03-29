package com.omgservers.service.bootstrap;

import com.omgservers.service.configuration.ServicePriorityConfiguration;
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
    void startup(@Observes @Priority(ServicePriorityConfiguration.START_UP_SCHEMA_MIGRATION_PRIORITY)
                 StartupEvent event) {
        if (getConfigOperation.getServiceConfig().migration().enabled()) {
            migrateSchemaOperation.migrateSystemSchema("db/system");
            migrateSchemaOperation.migrateShardsSchema("db/shards");
        } else {
            log.warn("Schema migration was disabled, skip operation");
        }
    }
}
