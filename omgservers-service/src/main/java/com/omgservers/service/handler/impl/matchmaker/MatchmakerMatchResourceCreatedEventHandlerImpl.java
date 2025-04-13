package com.omgservers.service.handler.impl.matchmaker;

import com.omgservers.schema.model.match.MatchConfigDto;
import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceModel;
import com.omgservers.schema.module.match.SyncMatchRequest;
import com.omgservers.schema.module.match.SyncMatchResponse;
import com.omgservers.schema.module.matchmaker.matchmakerMatchResource.GetMatchmakerMatchResourceRequest;
import com.omgservers.schema.module.matchmaker.matchmakerMatchResource.GetMatchmakerMatchResourceResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerMatchResourceCreatedEventBodyModel;
import com.omgservers.service.factory.match.MatchModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.shard.match.MatchShard;
import com.omgservers.service.shard.matchmaker.MatchmakerShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchmakerMatchResourceCreatedEventHandlerImpl implements EventHandler {

    final MatchmakerShard matchmakerShard;
    final MatchShard matchShard;

    final MatchModelFactory matchModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCHMAKER_MATCH_RESOURCE_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (MatchmakerMatchResourceCreatedEventBodyModel) event.getBody();
        final var matchmakerId = body.getMatchmakerId();
        final var id = body.getId();

        final var idempotencyKey = event.getId().toString();

        return getMatchmakerMatchResource(matchmakerId, id)
                .flatMap(matchmakerMatchResource -> {
                    log.debug("Created, {}", matchmakerMatchResource);
                    return createMatch(matchmakerMatchResource, idempotencyKey);
                })
                .replaceWithVoid();
    }

    Uni<MatchmakerMatchResourceModel> getMatchmakerMatchResource(final Long matchmakerId, final Long id) {
        final var request = new GetMatchmakerMatchResourceRequest(matchmakerId, id);
        return matchmakerShard.getService().execute(request)
                .map(GetMatchmakerMatchResourceResponse::getMatchmakerMatchResource);
    }

    Uni<Boolean> createMatch(final MatchmakerMatchResourceModel matchmakerMatchResource,
                             final String idempotencyKey) {
        final var matchmakerId = matchmakerMatchResource.getMatchmakerId();
        final var matchId = matchmakerMatchResource.getMatchId();
        final var mode = matchmakerMatchResource.getMode();
        final var match = matchModelFactory.create(matchId,
                matchmakerId,
                MatchConfigDto.create(mode),
                idempotencyKey);
        final var request = new SyncMatchRequest(match);
        return matchShard.getService().executeWithIdempotency(request)
                .map(SyncMatchResponse::getCreated);
    }
}
