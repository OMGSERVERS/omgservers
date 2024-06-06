package com.omgservers.service.module.system.impl.service.bootstrapService.impl.method.bootstrapDatabaseSchema;

import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;
import java.util.stream.IntStream;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class BootstrapDatabaseSchemaMethodImpl implements BootstrapDatabaseSchemaMethod {

    private static final String SYSTEM_SCHEMA_LOCATION = "db/system";
    private static final String SHARDS_SCHEMA_LOCATION = "db/shards";

    final GetConfigOperation getConfigOperation;

    final DataSource dataSource;

    @Override
    public Uni<Void> bootstrapDatabaseSchema() {
        log.debug("Bootstrap database schema");

        return Uni.createFrom().voidItem()
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .invoke(voidItem -> {
                    migrateSystemSchema(SYSTEM_SCHEMA_LOCATION);
                    migrateShardsSchema(SHARDS_SCHEMA_LOCATION);
                });
    }

    void migrateSystemSchema(final String location) {
        final var flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations(location)
                .createSchemas(true)
                .defaultSchema("system")
                .load();
        flyway.migrate();
    }

    public void migrateShardsSchema(final String location) {
        final var shardCount = getConfigOperation.getServiceConfig().index().shardCount();
        final var migrationConcurrency = getConfigOperation.getServiceConfig().bootstrap().schema().concurrency();
        final var migrationTasks = IntStream.range(0, shardCount)
                .mapToObj(shard -> migrateShard(location, shard)).toList();
        Uni.join().all(migrationTasks).usingConcurrencyOf(migrationConcurrency)
                .andFailFast().replaceWithVoid().await().indefinitely();
    }

    Uni<Void> migrateShard(final String location, final int shard) {
        return Uni.createFrom().voidItem()
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .invoke(voidItem -> {
                    Flyway flyway = Flyway.configure()
                            .dataSource(dataSource)
                            .locations(location)
                            .createSchemas(true)
                            .defaultSchema(String.format("shard_%05d", shard))
                            .load();
                    flyway.migrate();
                });
    }
}
