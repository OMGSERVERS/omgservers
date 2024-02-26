package com.omgservers.service.handler.matchmaker;

import com.omgservers.model.dto.matchmaker.GetMatchmakerRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerResponse;
import com.omgservers.model.dto.system.SyncEventRequest;
import com.omgservers.model.dto.system.SyncEventResponse;
import com.omgservers.model.dto.tenant.SyncVersionMatchmakerRefRequest;
import com.omgservers.model.dto.tenant.SyncVersionMatchmakerRefResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.MatchmakerCreatedEventBodyModel;
import com.omgservers.model.event.body.MatchmakerJobTaskExecutionRequestedEventBodyModel;
import com.omgservers.model.matchmaker.MatchmakerModel;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.EventModelFactory;
import com.omgservers.service.factory.VersionMatchmakerRefModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchmakerCreatedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final SystemModule systemModule;
    final TenantModule tenantModule;

    final VersionMatchmakerRefModelFactory versionMatchmakerRefModelFactory;
    final EventModelFactory eventModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCHMAKER_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (MatchmakerCreatedEventBodyModel) event.getBody();
        final var matchmakerId = body.getId();

        return getMatchmaker(matchmakerId)
                .flatMap(matchmaker -> {
                    log.info("Matchmaker was created, id={}, version={}/{}",
                            matchmaker.getId(),
                            matchmaker.getTenantId(),
                            matchmaker.getVersionId());

                    return syncVersionMatchmakerRef(matchmaker)
                            .flatMap(created -> requestJobExecution(matchmakerId));
                })
                .replaceWithVoid();
    }

    Uni<MatchmakerModel> getMatchmaker(final Long matchmakerId) {
        final var request = new GetMatchmakerRequest(matchmakerId);
        return matchmakerModule.getMatchmakerService().getMatchmaker(request)
                .map(GetMatchmakerResponse::getMatchmaker);
    }

    Uni<Boolean> syncVersionMatchmakerRef(final MatchmakerModel matchmaker) {
        final var tenantId = matchmaker.getTenantId();
        final var versionId = matchmaker.getVersionId();
        final var matchmakerId = matchmaker.getId();
        final var versionMatchmakerRef = versionMatchmakerRefModelFactory.create(tenantId, versionId, matchmakerId);
        final var request = new SyncVersionMatchmakerRefRequest(versionMatchmakerRef);
        return tenantModule.getVersionService().syncVersionMatchmakerRef(request)
                .map(SyncVersionMatchmakerRefResponse::getCreated)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithItem(Boolean.FALSE);
    }

    Uni<Boolean> requestJobExecution(final Long matchmakerId) {
        final var eventBody = new MatchmakerJobTaskExecutionRequestedEventBodyModel(matchmakerId);
        final var eventModel = eventModelFactory.create(eventBody);

        final var syncEventRequest = new SyncEventRequest(eventModel);
        return systemModule.getEventService().syncEvent(syncEventRequest)
                .map(SyncEventResponse::getCreated);
    }
}
