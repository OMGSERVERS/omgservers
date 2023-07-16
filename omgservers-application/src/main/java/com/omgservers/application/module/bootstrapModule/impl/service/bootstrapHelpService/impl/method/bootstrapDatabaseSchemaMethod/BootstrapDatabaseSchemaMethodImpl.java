package com.omgservers.application.module.bootstrapModule.impl.service.bootstrapHelpService.impl.method.bootstrapDatabaseSchemaMethod;

import com.omgservers.application.operation.getConfigOperation.GetConfigOperation;
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

    final GetConfigOperation getConfigOperation;
    final DataSource dataSource;

    @Override
    public Uni<Void> bootstrapDatabaseSchema() {
        if (getConfigOperation.getConfig().disableMigration()) {
            return Uni.createFrom().voidItem()
                    .invoke(voidItem -> log.warn("Migration was disabled, skip operation"));
        } else {
            return Uni.createFrom().voidItem()
                    .flatMap(voidItem -> migrateInternalSchema())
                    .flatMap(voidItem -> {
                        final var shardCount = getConfigOperation.getConfig().shardCount();
                        final var migrationConcurrency = getConfigOperation.getConfig().migrationConcurrency();
                        final var migrationTasks = IntStream.range(0, shardCount).mapToObj(this::migrateShard).toList();
                        return Uni.join().all(migrationTasks).usingConcurrencyOf(migrationConcurrency)
                                .andFailFast().replaceWithVoid();
                    })
                    .invoke(voidItem -> log.info("Migration was finished"));
        }
    }

    Uni<Void> migrateInternalSchema() {
        return Uni.createFrom().voidItem()
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .invoke(voidItem -> {
                    Flyway flyway = getFlywayForInternalSchema();
                    flyway.migrate();
                    log.info("Internal schema migration was finished");
                });
    }

    Uni<Void> migrateShard(final int shard) {
        return Uni.createFrom().voidItem()
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .invoke(voidItem -> {
                    Flyway flyway = getFlywayForShardSchema(shard);
                    flyway.migrate();
                    log.info("Shard schema migration was finished, shard={}", shard);
                });
    }

    Flyway getFlywayForInternalSchema() {
        return Flyway.configure()
                .dataSource(dataSource)
                .locations("db/internal")
                .createSchemas(true)
                .defaultSchema("internal")
                .load();
    }

    Flyway getFlywayForShardSchema(Integer shard) {
        return Flyway.configure()
                .dataSource(dataSource)
                .locations("db/shard")
                .createSchemas(true)
                .defaultSchema(String.format("shard_%05d", shard))
                .load();
    }
}
