package com.omgservers.service.server.initializer.impl.method;

import com.omgservers.schema.model.index.IndexModel;
import com.omgservers.schema.model.index.IndexServerDto;
import com.omgservers.service.configuration.DatabaseSchemaConfiguration;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import com.omgservers.service.server.index.IndexService;
import com.omgservers.service.server.index.dto.GetIndexRequest;
import com.omgservers.service.server.index.dto.GetIndexResponse;
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
class InitializeShardsSchemasMethodImpl implements InitializeShardsSchemasMethod {

    private static final String SHARDS_SCHEMA_LOCATION = "db/shards";

    final GetServiceConfigOperation getServiceConfigOperation;

    final IndexService indexService;

    final DataSource dataSource;

    @Override
    public Uni<Void> execute() {
        log.debug("Initialize database schema");

        return getIndex()
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .flatMap(this::migrateShardsSchema);
    }

    Uni<IndexModel> getIndex() {
        final var request = new GetIndexRequest();
        return indexService.getIndex(request)
                .map(GetIndexResponse::getIndex);
    }

    public Uni<Void> migrateShardsSchema(final IndexModel index) {
        final var serverUri = getServiceConfigOperation.getServiceConfig().server().uri();
        return index.getConfig().getServers().stream()
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
