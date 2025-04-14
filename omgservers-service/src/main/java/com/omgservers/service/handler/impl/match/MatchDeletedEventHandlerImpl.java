package com.omgservers.service.handler.impl.match;

import com.omgservers.schema.model.match.MatchModel;
import com.omgservers.schema.shard.match.GetMatchRequest;
import com.omgservers.schema.shard.match.GetMatchResponse;
import com.omgservers.schema.shard.runtime.runtime.DeleteRuntimeRequest;
import com.omgservers.schema.shard.runtime.runtime.DeleteRuntimeResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.match.MatchDeletedEventBodyModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.shard.match.MatchShard;
import com.omgservers.service.shard.runtime.RuntimeShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchDeletedEventHandlerImpl implements EventHandler {

    final RuntimeShard runtimeShard;
    final MatchShard matchShard;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCH_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (MatchDeletedEventBodyModel) event.getBody();
        final var matchId = body.getId();

        final var idempotencyKey = event.getId().toString();

        return getMatch(matchId)
                .flatMap(match -> {
                    log.debug("Deleted, {}", match);

                    return deleteRuntime(match.getRuntimeId());
                })
                .replaceWithVoid();
    }

    Uni<MatchModel> getMatch(final Long id) {
        final var request = new GetMatchRequest(id);
        return matchShard.getService().execute(request)
                .map(GetMatchResponse::getMatch);
    }

    Uni<Boolean> deleteRuntime(final Long runtimeId) {
        final var request = new DeleteRuntimeRequest(runtimeId);
        return runtimeShard.getService().execute(request)
                .map(DeleteRuntimeResponse::getDeleted);
    }
}
