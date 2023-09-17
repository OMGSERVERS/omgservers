package com.omgservers.handler;

import com.omgservers.module.system.SystemModule;
import com.omgservers.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.dto.internal.UnscheduleJobRequest;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.JobDeletedEventBodyModel;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class JobDeletedEventHandlerImpl implements EventHandler {

    final SystemModule systemModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.JOB_DELETED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (JobDeletedEventBodyModel) event.getBody();
        final var shardKey = body.getShardKey();
        final var entityId = body.getEntityId();
        final var request = new UnscheduleJobRequest(shardKey, entityId);
        return systemModule.getJobService().unscheduleJob(request)
                .replaceWith(true);
    }
}
