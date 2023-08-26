package com.omgservers;

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
public class Migration {

    final MigrateOperation migrateOperation;

    @WithSpan
    @PostConstruct
    void postConstruct() {
        migrateOperation.migrateInternalSchema("db/internal", "tab_migration_internal");
        migrateOperation.migrateShardsSchema("db/shards", "tab_migration_shards");
    }
}
