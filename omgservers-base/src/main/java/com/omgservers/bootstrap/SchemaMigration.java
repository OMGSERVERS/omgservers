package com.omgservers.bootstrap;

import com.omgservers.operation.getConfig.GetConfigOperation;
import com.omgservers.operation.migrateOperation.MigrateOperation;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Startup
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class SchemaMigration {

    final GetConfigOperation getConfigOperation;
    final MigrateOperation migrateOperation;

    @WithSpan
    @PostConstruct
    void postConstruct() {
        if (getConfigOperation.getConfig().disableMigration()) {
            log.warn("SchemaMigration was disabled, skip");
        } else {
            migrateOperation.migrateInternalSchema("db/internal");
            migrateOperation.migrateShardsSchema("db/shards");
        }
    }
}
