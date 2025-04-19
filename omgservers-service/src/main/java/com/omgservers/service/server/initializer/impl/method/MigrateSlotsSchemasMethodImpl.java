package com.omgservers.service.server.initializer.impl.method;

import com.omgservers.schema.model.index.IndexShardDto;
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
class MigrateSlotsSchemasMethodImpl implements MigrateSlotsSchemasMethod {

    private static final String SCRIPTS_LOCATION = "db/slot";

    final GetServiceConfigOperation getServiceConfigOperation;
    final GetIndexConfigOperation getIndexConfigOperation;

    final DataSource dataSource;

    @Override
    public Uni<Void> execute() {
        log.debug("Migrate \"{}\"", SCRIPTS_LOCATION);

        return Uni.createFrom().voidItem()
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .flatMap(voidItem -> migrateSchemas())
                .invoke(voidItem -> log.info("Slots schemas migrated"));
    }

    public Uni<Void> migrateSchemas() {
        return getIndexConfigOperation.execute()
                .flatMap(indexConfig -> {
                    final var shardUri = getServiceConfigOperation.getServiceConfig().shard().uri();
                    return indexConfig.getShards().stream()
                            .filter(s -> s.getUri().equals(shardUri))
                            .map(IndexShardDto::getSlots)
                            .findFirst()
                            .map(slots -> {
                                final var concurrency = getServiceConfigOperation.getServiceConfig()
                                        .migration().concurrency();
                                final var migrationTasks = slots.stream()
                                        .map(this::migrateSlotSchema)
                                        .toList();
                                return Uni.join().all(migrationTasks)
                                        .usingConcurrencyOf(concurrency)
                                        .andFailFast().replaceWithVoid();
                            })
                            .orElse(Uni.createFrom().voidItem());
                });
    }

    Uni<Void> migrateSlotSchema(final int slot) {
        return Uni.createFrom().voidItem()
                .invoke(voidItem -> {
                    final var flyway = Flyway.configure()
                            .dataSource(dataSource)
                            .locations(SCRIPTS_LOCATION)
                            .createSchemas(true)
                            .defaultSchema(String.format(DatabaseSchemaConfiguration.SLOT_SCHEMA_FORMAT, slot))
                            .load();
                    flyway.migrate();
                });
    }
}
