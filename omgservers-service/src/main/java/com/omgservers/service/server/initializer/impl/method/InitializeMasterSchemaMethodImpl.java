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
class InitializeMasterSchemaMethodImpl implements InitializeMasterSchemaMethod {

    private static final String MASTER_SCHEMA_LOCATION = "db/master";

    final GetServiceConfigOperation getServiceConfigOperation;

    final DataSource dataSource;

    @Override
    public Uni<Void> execute() {
        log.debug("Initialize master schema");

        return Uni.createFrom().voidItem()
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .invoke(voidItem -> migrateMasterSchema());
    }

    void migrateMasterSchema() {
        final var serverId = getServiceConfigOperation.getServiceConfig().server().id();
        final var flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations(MASTER_SCHEMA_LOCATION)
                .createSchemas(true)
                .defaultSchema(String.format(DatabaseSchemaConfiguration.MASTER_SCHEMA_FORMAT, serverId))
                .load();
        flyway.migrate();
    }
}
