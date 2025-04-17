package com.omgservers.service.server.initializer.impl.method;

import com.omgservers.schema.model.index.IndexServerDto;
import com.omgservers.service.configuration.DatabaseSchemaConfiguration;
import com.omgservers.service.operation.server.GetIndexConfigOperation;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class MigrateShardsSchemasMethodImpl implements MigrateShardsSchemasMethod {

    private static final String SHARDS_SCHEMA_LOCATION = "db/shards";

    final GetServiceConfigOperation getServiceConfigOperation;
    final GetIndexConfigOperation getIndexConfigOperation;

    final DataSource dataSource;

    @Override
    public Uni<Void> execute() {
        log.debug("Migrate {} schema", SHARDS_SCHEMA_LOCATION);

        return Uni.createFrom().voidItem()
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .flatMap(voidItem -> migrateShardsSchema())
                .invoke(voidItem -> log.info("Shards schemas migrated"));
    }

    public Uni<Void> migrateShardsSchema() {
        return getIndexConfigOperation.execute()
                .flatMap(indexConfig -> {
                    final var serverUri = getServiceConfigOperation.getServiceConfig().server().uri();
                    return indexConfig.getServers().stream()
                            .filter(s -> s.getUri().equals(serverUri))
                            .map(IndexServerDto::getShards)
                            .findFirst()
                            .map(serverShards -> {
                                final var migrationConcurrency = getServiceConfigOperation.getServiceConfig()
                                        .initialization().databaseSchema().concurrency();
                                final var migrationTasks = serverShards.stream()
                                        .map(this::migrateShard)
                                        .toList();
                                return Uni.join().all(migrationTasks)
                                        .usingConcurrencyOf(migrationConcurrency)
                                        .andFailFast().replaceWithVoid();
                            })
                            .orElse(Uni.createFrom().voidItem());
                });
    }

    Uni<Void> migrateShard(final int shard) {
        return Uni.createFrom().voidItem()
                .invoke(voidItem -> {
                    final var flyway = Flyway.configure()
                            .dataSource(dataSource)
                            .locations(SHARDS_SCHEMA_LOCATION)
                            .createSchemas(true)
                            .defaultSchema(String.format(DatabaseSchemaConfiguration.SHARD_SCHEMA_FORMAT, shard))
                            .load();
                    flyway.migrate();
                });
    }
}
