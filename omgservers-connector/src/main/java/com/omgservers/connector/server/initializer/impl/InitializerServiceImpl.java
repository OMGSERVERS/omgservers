package com.omgservers.connector.server.initializer.impl;

import com.omgservers.connector.server.initializer.InitializerService;
import com.omgservers.connector.server.initializer.impl.method.CreateConnectorTokenMethod;
import com.omgservers.connector.server.initializer.impl.method.ScheduleIdleConnectionsHandlerJobMethod;
import com.omgservers.connector.server.initializer.impl.method.ScheduleMessageInterchangerJobMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class InitializerServiceImpl implements InitializerService {

    final ScheduleIdleConnectionsHandlerJobMethod scheduleIdleConnectionsHandlerJobMethod;
    final ScheduleMessageInterchangerJobMethod scheduleMessageInterchangerJobMethod;
    final CreateConnectorTokenMethod createConnectorTokenMethod;

    @Override
    public Uni<Void> initialize() {
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> createConnectorToken())
                .flatMap(voidItem -> scheduleIdleConnectionsHandlerJob())
                .flatMap(voidItem -> scheduleMessageInterchangerJob());
    }

    Uni<Void> createConnectorToken() {
        return Uni.createFrom().voidItem()
                .invoke(voidItem -> createConnectorTokenMethod.execute());
    }

    Uni<Void> scheduleIdleConnectionsHandlerJob() {
        return Uni.createFrom().voidItem()
                .invoke(voidItem -> scheduleIdleConnectionsHandlerJobMethod.execute());
    }

    Uni<Void> scheduleMessageInterchangerJob() {
        return Uni.createFrom().voidItem()
                .invoke(voidItem -> scheduleMessageInterchangerJobMethod.execute());
    }
}
