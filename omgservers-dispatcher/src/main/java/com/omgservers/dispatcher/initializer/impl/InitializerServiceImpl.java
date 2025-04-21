package com.omgservers.dispatcher.initializer.impl;

import com.omgservers.dispatcher.initializer.InitializerService;
import com.omgservers.dispatcher.initializer.impl.method.ScheduleIdleConnectionsHandlerJobMethod;
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

    @Override
    public Uni<Void> initialize() {
        return scheduleIdleConnectionsHandlerJob();
    }

    Uni<Void> scheduleIdleConnectionsHandlerJob() {
        return Uni.createFrom().voidItem()
                .invoke(voidItem -> scheduleIdleConnectionsHandlerJobMethod.execute());
    }
}
