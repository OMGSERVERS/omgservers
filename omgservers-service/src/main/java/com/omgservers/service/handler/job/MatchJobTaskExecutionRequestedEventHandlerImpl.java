package com.omgservers.service.handler.job;

import com.omgservers.model.dto.system.SyncEventRequest;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.MatchJobTaskExecutionRequestedEventBodyModel;
import com.omgservers.service.factory.EventModelFactory;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.handler.job.task.match.MatchJobTaskImpl;
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
public class MatchJobTaskExecutionRequestedEventHandlerImpl implements EventHandler {

    static private final int JOB_INTERVAL_IN_SECONDS = 1;

    final MatchJobTaskImpl matchJobTask;
    final SystemModule systemModule;

    final EventModelFactory eventModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCH_JOB_TASK_EXECUTION_REQUESTED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (MatchJobTaskExecutionRequestedEventBodyModel) event.getBody();
        final var matchmakerId = body.getMatchmakerId();
        final var matchId = body.getMatchId();

        return matchJobTask.executeTask(matchmakerId, matchId)
                .onFailure()
                .recoverWithUni(t -> {
                    log.warn("Job task failed, {}:{}", t.getClass().getSimpleName(), t.getMessage());
                    return Uni.createFrom().item(true);
                })
                .flatMap(oneMoreTime -> {
                    if (oneMoreTime) {
                        return requestFurtherExecution(matchmakerId, matchId);
                    } else {
                        return Uni.createFrom().voidItem();
                    }
                });
    }

    Uni<Void> requestFurtherExecution(final Long matchmakerId, final Long matchId) {
        final var eventBody = new MatchJobTaskExecutionRequestedEventBodyModel(matchmakerId, matchId);
        final var eventModel = eventModelFactory.create(eventBody);
        eventModel.setDelayed(Instant.now().plus(Duration.ofSeconds(JOB_INTERVAL_IN_SECONDS)));

        final var syncEventRequest = new SyncEventRequest(eventModel);
        return systemModule.getEventService().syncEvent(syncEventRequest)
                .replaceWithVoid();
    }
}
