package com.omgservers.service.server.initializer.impl;

import com.omgservers.service.configuration.ServicePriorityConfiguration;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import com.omgservers.service.server.initializer.InitializerService;
import com.omgservers.service.server.initializer.impl.method.AcquireNodeIdMethod;
import com.omgservers.service.server.initializer.impl.method.CreateIndexMethod;
import com.omgservers.service.server.initializer.impl.method.IssueServiceTokenMethod;
import com.omgservers.service.server.initializer.impl.method.MigrateMasterSchemaMethod;
import com.omgservers.service.server.initializer.impl.method.MigrateServerSchemaMethod;
import com.omgservers.service.server.initializer.impl.method.MigrateShardsSchemasMethod;
import com.omgservers.service.server.initializer.impl.method.ScheduleBootstrapJobMethod;
import com.omgservers.service.server.initializer.impl.method.ScheduleEventHandlerJobMethod;
import com.omgservers.service.server.initializer.impl.method.ScheduleSchedulerJobMethod;
import com.omgservers.service.server.initializer.impl.method.SetIndexConfigMethod;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.quarkus.runtime.StartupEvent;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class InitializerServiceImpl implements InitializerService {

    final ScheduleEventHandlerJobMethod scheduleEventHandlerJobMethod;
    final MigrateShardsSchemasMethod migrateShardsSchemasMethod;
    final ScheduleSchedulerJobMethod scheduleSchedulerJobMethod;
    final ScheduleBootstrapJobMethod scheduleBootstrapJobMethod;
    final MigrateServerSchemaMethod migrateServerSchemaMethod;
    final MigrateMasterSchemaMethod migrateMasterSchemaMethod;
    final IssueServiceTokenMethod issueServiceTokenMethod;
    final AcquireNodeIdMethod acquireNodeIdMethod;
    final CreateIndexMethod createIndexMethod;
    final SetIndexConfigMethod setIndexConfigMethod;

    final GetServiceConfigOperation getServiceConfigOperation;

    @WithSpan
    void startup(@Observes @Priority(ServicePriorityConfiguration.START_UP_BOOTSTRAP_SERVICE_PRIORITY)
                 final StartupEvent event) {
        initialize().await().indefinitely();
    }

    @Override
    public Uni<Void> initialize() {
        return migrateMasterSchema()
                .flatMap(voidItem -> issueServiceToken())
                .flatMap(voidItem -> acquireNodeId())
                .flatMap(voidItem -> migrateServerSchema())
                .flatMap(voidItem -> createIndex())
                .flatMap(voidItem -> setIndexConfig())
                .flatMap(voidItem -> migrateShardsSchemas())
                .flatMap(voidItem -> scheduleEventHandlerJob())
                .flatMap(voidItem -> scheduleSchedulerJob())
                .flatMap(voidItem -> scheduleBootstrapJob());
    }

    Uni<Void> migrateMasterSchema() {
        return migrateMasterSchemaMethod.execute();
    }

    Uni<Void> acquireNodeId() {
        return acquireNodeIdMethod.execute();
    }

    Uni<Void> migrateServerSchema() {
        return migrateServerSchemaMethod.execute();
    }

    Uni<Void> createIndex() {
        return createIndexMethod.execute();
    }

    Uni<Void> issueServiceToken() {
        return Uni.createFrom().voidItem()
                .invoke(voidItem -> issueServiceTokenMethod.execute());
    }

    Uni<Void> setIndexConfig() {
        return setIndexConfigMethod.execute();
    }

    Uni<Void> migrateShardsSchemas() {
        return migrateShardsSchemasMethod.execute();
    }

    Uni<Void> scheduleEventHandlerJob() {
        if (!getServiceConfigOperation.getServiceConfig().initialization().eventHandlerJob().enabled()) {
            return Uni.createFrom().voidItem();
        }

        return Uni.createFrom().voidItem()
                .invoke(voidItem -> scheduleEventHandlerJobMethod.execute());
    }

    Uni<Void> scheduleSchedulerJob() {
        if (!getServiceConfigOperation.getServiceConfig().initialization().schedulerJob().enabled()) {
            return Uni.createFrom().voidItem();
        }

        return Uni.createFrom().voidItem()
                .invoke(voidItem -> scheduleSchedulerJobMethod.execute());
    }

    Uni<Void> scheduleBootstrapJob() {
        if (!getServiceConfigOperation.getServiceConfig().initialization().bootstrapJob().enabled()) {
            return Uni.createFrom().voidItem();
        }

        return Uni.createFrom().voidItem()
                .invoke(voidItem -> scheduleBootstrapJobMethod.execute());
    }
}
