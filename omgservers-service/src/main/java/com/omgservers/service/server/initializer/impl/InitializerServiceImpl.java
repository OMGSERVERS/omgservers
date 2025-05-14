package com.omgservers.service.server.initializer.impl;

import com.omgservers.service.operation.server.GetServiceConfigOperation;
import com.omgservers.service.server.initializer.InitializerService;
import com.omgservers.service.server.initializer.impl.method.AcquireNodeIdMethod;
import com.omgservers.service.server.initializer.impl.method.CreateIndexMethod;
import com.omgservers.service.server.initializer.impl.method.IssueServiceTokenMethod;
import com.omgservers.service.server.initializer.impl.method.MigrateMasterSchemaMethod;
import com.omgservers.service.server.initializer.impl.method.MigrateShardSchemaMethod;
import com.omgservers.service.server.initializer.impl.method.MigrateSlotsSchemasMethod;
import com.omgservers.service.server.initializer.impl.method.ReadX5CMethod;
import com.omgservers.service.server.initializer.impl.method.ScheduleBootstrapJobMethod;
import com.omgservers.service.server.initializer.impl.method.ScheduleEventHandlerJobMethod;
import com.omgservers.service.server.initializer.impl.method.ScheduleSchedulerJobMethod;
import com.omgservers.service.server.initializer.impl.method.SetIndexConfigMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class InitializerServiceImpl implements InitializerService {

    final ScheduleEventHandlerJobMethod scheduleEventHandlerJobMethod;
    final ScheduleSchedulerJobMethod scheduleSchedulerJobMethod;
    final ScheduleBootstrapJobMethod scheduleBootstrapJobMethod;
    final MigrateSlotsSchemasMethod migrateSlotsSchemasMethod;
    final MigrateMasterSchemaMethod migrateMasterSchemaMethod;
    final MigrateShardSchemaMethod migrateShardSchemaMethod;
    final IssueServiceTokenMethod issueServiceTokenMethod;
    final SetIndexConfigMethod setIndexConfigMethod;
    final AcquireNodeIdMethod acquireNodeIdMethod;
    final CreateIndexMethod createIndexMethod;
    final ReadX5CMethod readX5CMethod;

    final GetServiceConfigOperation getServiceConfigOperation;

    @Override
    public Uni<Void> initialize() {
        return migrateMasterSchema()
                .flatMap(voidItem -> issueServiceToken())
                .flatMap(voidItem -> acquireNodeId())
                .flatMap(voidItem -> migrateShardSchema())
                .flatMap(voidItem -> createIndex())
                .flatMap(voidItem -> setIndexConfig())
                .flatMap(voidItem -> migrateSlotsSchemas())
                .flatMap(voidItem -> readX5C())
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

    Uni<Void> migrateShardSchema() {
        return migrateShardSchemaMethod.execute();
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

    Uni<Void> migrateSlotsSchemas() {
        return migrateSlotsSchemasMethod.execute();
    }

    Uni<Void> readX5C() {
        return readX5CMethod.execute();
    }

    Uni<Void> scheduleEventHandlerJob() {
        if (!getServiceConfigOperation.getServiceConfig().jobs().eventHandler().enabled()) {
            return Uni.createFrom().voidItem();
        }

        return Uni.createFrom().voidItem()
                .invoke(voidItem -> scheduleEventHandlerJobMethod.execute());
    }

    Uni<Void> scheduleSchedulerJob() {
        if (!getServiceConfigOperation.getServiceConfig().jobs().scheduler().enabled()) {
            return Uni.createFrom().voidItem();
        }

        return Uni.createFrom().voidItem()
                .invoke(voidItem -> scheduleSchedulerJobMethod.execute());
    }

    Uni<Void> scheduleBootstrapJob() {
        if (!getServiceConfigOperation.getServiceConfig().jobs().bootstrap().enabled()) {
            return Uni.createFrom().voidItem();
        }

        return Uni.createFrom().voidItem()
                .invoke(voidItem -> scheduleBootstrapJobMethod.execute());
    }
}
