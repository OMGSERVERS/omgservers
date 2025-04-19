package com.omgservers.service.server.initializer.impl.method;

import com.omgservers.service.configuration.DatabaseSchemaConfiguration;
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
class MigrateShardSchemaMethodImpl implements MigrateShardSchemaMethod {

    private static final String SCRIPTS_LOCATION = "db/shard";

    final GetServiceConfigOperation getServiceConfigOperation;

    final DataSource dataSource;

    @Override
    public Uni<Void> execute() {
        log.debug("Migrate \"{}\"", SCRIPTS_LOCATION);

        return Uni.createFrom().voidItem()
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .invoke(voidItem -> migrateServerSchema())
                .invoke(voidItem -> log.info("Shard schema migrated"));
    }

    void migrateServerSchema() {
        final var shardId = getServiceConfigOperation.getServiceConfig().shard().id();
        final var flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations(SCRIPTS_LOCATION)
                .createSchemas(true)
                .defaultSchema(String.format(DatabaseSchemaConfiguration.SHARD_SCHEMA_FORMAT, shardId))
                .load();
        flyway.migrate();
    }
}
