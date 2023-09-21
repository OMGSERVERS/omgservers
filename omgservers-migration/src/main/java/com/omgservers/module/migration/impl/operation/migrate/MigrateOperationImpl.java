package com.omgservers.module.migration.impl.operation.migrate;

import com.omgservers.operation.getConfig.GetConfigOperation;
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
class MigrateOperationImpl implements MigrateOperation {

    final GetConfigOperation getConfigOperation;
    final DataSource dataSource;

    @Override
    public void migrateSystemSchema(String location) {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations(location)
                .createSchemas(true)
                .defaultSchema("system")
                .load();
        flyway.migrate();
        log.info("System schema migration was finished, location={}", location);
    }

    @Override
    public void migrateShardsSchema(String location) {
        final var shardCount = getConfigOperation.getConfig().shardCount();
        final var migrationConcurrency = getConfigOperation.getConfig().migrationConcurrency();
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
                    log.info("Shard schema migration was finished, location={}, shard={}", location, shard);
                });
    }
}
