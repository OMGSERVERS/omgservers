package com.omgservers.service.handler.impl.matchmaker;

import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceModel;
import com.omgservers.schema.shard.match.DeleteMatchRequest;
import com.omgservers.schema.shard.match.DeleteMatchResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.GetMatchmakerMatchResourceRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.GetMatchmakerMatchResourceResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerMatchResourceDeletedEventBodyModel;
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
public class MatchmakerMatchResourceDeletedEventHandlerImpl implements EventHandler {

    final MatchmakerShard matchmakerShard;
    final MatchShard matchShard;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCHMAKER_MATCH_RESOURCE_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (MatchmakerMatchResourceDeletedEventBodyModel) event.getBody();
        final var matchmakerId = body.getMatchmakerId();
        final var id = body.getId();

        return getMatchmakerMatchResource(matchmakerId, id)
                .flatMap(matchmakerMatchResource -> {
                    log.debug("Deleted, {}", matchmakerMatchResource);

                    final var matchId = matchmakerMatchResource.getMatchId();
                    return deleteMatch(matchId);
                })
                .replaceWithVoid();
    }

    Uni<MatchmakerMatchResourceModel> getMatchmakerMatchResource(final Long matchmakerId, final Long id) {
        final var request = new GetMatchmakerMatchResourceRequest(matchmakerId, id);
        return matchmakerShard.getService().execute(request)
                .map(GetMatchmakerMatchResourceResponse::getMatchmakerMatchResource);
    }

    Uni<Boolean> deleteMatch(final Long matchId) {
        final var request = new DeleteMatchRequest(matchId);
        return matchShard.getService().execute(request)
                .map(DeleteMatchResponse::getDeleted);
    }
}
