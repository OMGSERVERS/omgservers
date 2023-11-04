package com.omgservers.service.module.migration.impl.bootstrap;

import com.omgservers.service.module.migration.impl.operation.migrate.MigrateOperation;
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
public class SchemaMigration {

    final GetConfigOperation getConfigOperation;
    final MigrateOperation migrateOperation;

    @WithSpan
    void startup(@Observes @Priority(1000) StartupEvent event) {
        if (getConfigOperation.getConfig().disableMigration()) {
            log.warn("Schema migration was disabled, skip operation");
        } else {
            migrateOperation.migrateSystemSchema("db/system");
            migrateOperation.migrateShardsSchema("db/shards");
        }
    }
}
