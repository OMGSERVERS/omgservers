package com.omgservers.operation.migrateOperation;

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
    public void migrateInternalSchema(String location, String table) {
        if (getConfigOperation.getConfig().disableMigration()) {
            log.warn("Migration was disabled, skip operation, location={}", location);
        } else {
            Flyway flyway = Flyway.configure()
                    .dataSource(dataSource)
                    .locations(location)
//                    .table(table)
//                    .baselineOnMigrate(true)
                    .createSchemas(true)
                    .defaultSchema("internal")
                    .load();
            flyway.migrate();
            log.info("Internal schema migration was finished, location={}", location);
        }
    }

    @Override
    public void migrateShardsSchema(String location, String table) {
        if (getConfigOperation.getConfig().disableMigration()) {
            log.warn("Migration was disabled, skip operation, location={}", location);
        } else {
            final var shardCount = getConfigOperation.getConfig().shardCount();
            final var migrationConcurrency = getConfigOperation.getConfig().migrationConcurrency();
            final var migrationTasks = IntStream.range(0, shardCount)
                    .mapToObj(shard -> migrateShard(location, table, shard)).toList();
            Uni.join().all(migrationTasks).usingConcurrencyOf(migrationConcurrency)
                    .andFailFast().replaceWithVoid().await().indefinitely();
        }
    }

    Uni<Void> migrateShard(final String location, final String table, final int shard) {
        return Uni.createFrom().voidItem()
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .invoke(voidItem -> {
                    Flyway flyway = Flyway.configure()
                            .dataSource(dataSource)
                            .locations(location)
//                            .table(table)
//                            .baselineOnMigrate(true)
                            .createSchemas(true)
                            .defaultSchema(String.format("shard_%05d", shard))
                            .load();
                    flyway.migrate();
                    log.info("Shard schema migration was finished, location={}, shard={}", location, shard);
                });
    }
}
