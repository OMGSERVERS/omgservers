package com.omgservers.service.handler.system;

import com.omgservers.model.dto.system.DeleteJobRequest;
import com.omgservers.model.dto.system.DeleteJobResponse;
import com.omgservers.model.dto.system.FindJobRequest;
import com.omgservers.model.dto.system.FindJobResponse;
import com.omgservers.model.dto.system.GetHandlerRequest;
import com.omgservers.model.dto.system.GetHandlerResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.HandlerDeletedEventBodyModel;
import com.omgservers.model.handler.HandlerModel;
import com.omgservers.model.job.JobModel;
import com.omgservers.model.job.JobQualifierEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.system.SystemModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class HandlerDeletedEventHandlerImpl implements EventHandler {

    final SystemModule systemModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.HANDLER_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (HandlerDeletedEventBodyModel) event.getBody();
        final var handlerId = body.getId();

        return getHandler(handlerId)
                .flatMap(stage -> {
                    log.info("Handler was deleted, handlerId={}", handlerId);

                    return findAndDeleteHandlerJob(handlerId);
                })
                .replaceWithVoid();
    }

    Uni<HandlerModel> getHandler(final Long id) {
        final var request = new GetHandlerRequest(id);
        return systemModule.getHandlerService().getHandler(request)
                .map(GetHandlerResponse::getHandler);
    }

    Uni<Boolean> findAndDeleteHandlerJob(final Long handlerId) {
        return findHandlerJob(handlerId)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithNull()
                .onItem().ifNotNull().transformToUni(job -> deleteJob(job.getId()));
    }

    Uni<JobModel> findHandlerJob(final Long handlerId) {
        final var request = new FindJobRequest(handlerId, handlerId, JobQualifierEnum.HANDLER);
        return systemModule.getJobService().findJob(request)
                .map(FindJobResponse::getJob);
    }

    Uni<Boolean> deleteJob(final Long id) {
        final var request = new DeleteJobRequest(id);
        return systemModule.getJobService().deleteJob(request)
                .map(DeleteJobResponse::getDeleted);
    }
}
