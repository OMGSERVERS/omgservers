package com.omgservers.service.handler.job.matchmaker;

import com.omgservers.model.dto.system.SyncEventRequest;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.job.MatchmakerJobTaskExecutionRequestedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.system.SystemModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchmakerJobTaskExecutionRequestedEventHandlerImpl implements EventHandler {

    static private final int JOB_INTERVAL_IN_SECONDS = 1;

    final MatchmakerJobTaskImpl matchmakerJobTask;
    final SystemModule systemModule;

    final EventModelFactory eventModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCHMAKER_JOB_TASK_EXECUTION_REQUESTED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (MatchmakerJobTaskExecutionRequestedEventBodyModel) event.getBody();
        final var matchmakerId = body.getMatchmakerId();

        return matchmakerJobTask.executeTask(matchmakerId)
                .onFailure()
                .recoverWithUni(t -> {
                    log.warn("Job task failed, matchmakerId={}, {}:{}",
                            matchmakerId, t.getClass().getSimpleName(), t.getMessage());
                    return Uni.createFrom().item(Boolean.TRUE);
                })
                .flatMap(oneMoreTime -> {
                    if (oneMoreTime) {
                        return requestFurtherExecution(matchmakerId);
                    } else {
                        return Uni.createFrom().voidItem();
                    }
                });
    }

    Uni<Void> requestFurtherExecution(final Long matchmakerId) {
        final var eventBody = new MatchmakerJobTaskExecutionRequestedEventBodyModel(matchmakerId);
        final var eventModel = eventModelFactory.create(eventBody);
        eventModel.setDelayed(Instant.now().plus(Duration.ofSeconds(JOB_INTERVAL_IN_SECONDS)));

        final var syncEventRequest = new SyncEventRequest(eventModel);
        return systemModule.getEventService().syncEvent(syncEventRequest)
                .replaceWithVoid();
    }
}
