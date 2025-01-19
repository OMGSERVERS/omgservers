package com.omgservers.service.service.initializer.impl.method;

import com.omgservers.service.operation.server.GetServiceConfigOperation;
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
class InitializeDatabaseSchemaMethodImpl implements InitializeDatabaseSchemaMethod {

    private static final String SYSTEM_SCHEMA_LOCATION = "db/system";
    private static final String SHARDS_SCHEMA_LOCATION = "db/shards";

    final GetServiceConfigOperation getServiceConfigOperation;

    final DataSource dataSource;

    @Override
    public Uni<Void> execute() {
        log.debug("Initialize database schema");

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
        final var shardCount = getServiceConfigOperation.getServiceConfig().server().shardCount();
        final var migrationConcurrency = getServiceConfigOperation.getServiceConfig().initialization().databaseSchema().concurrency();
        final var migrationTasks = IntStream.range(0, shardCount)
                .mapToObj(shard -> migrateShard(location, shard)).toList();
        Uni.join().all(migrationTasks).usingConcurrencyOf(migrationConcurrency)
                .andFailFast().replaceWithVoid().await().indefinitely();
    }

    Uni<Void> migrateShard(final String location, final int shard) {
        return Uni.createFrom().voidItem()
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
