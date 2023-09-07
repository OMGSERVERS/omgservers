package com.omgservers.handler;

import com.omgservers.module.system.SystemModule;
import com.omgservers.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.dto.internal.ScheduleJobRequest;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.JobCreatedEventBodyModel;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class JobCreatedEventHandlerImpl implements EventHandler {

    final SystemModule systemModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.JOB_CREATED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (JobCreatedEventBodyModel) event.getBody();
        final var shardKey = body.getShardKey();
        final var entity = body.getEntity();
        final var type = body.getType();
        final var request = new ScheduleJobRequest(shardKey, entity, type);
        return systemModule.getJobService().scheduleJob(request)
                .replaceWith(true);
    }
}
