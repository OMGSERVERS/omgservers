package com.omgservers.service.handler.impl.matchmaker;

import com.omgservers.schema.model.matchmaker.MatchmakerModel;
import com.omgservers.schema.module.matchmaker.matchmaker.GetMatchmakerRequest;
import com.omgservers.schema.module.matchmaker.matchmaker.GetMatchmakerResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerDeletedEventBodyModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.operation.job.FindAndDeleteJobOperation;
import com.omgservers.service.operation.matchmaker.DeleteMatchmakerCommandsOperation;
import com.omgservers.service.operation.matchmaker.DeleteMatchmakerMatchesOperation;
import com.omgservers.service.operation.matchmaker.DeleteMatchmakerRequestsOperation;
import com.omgservers.service.shard.matchmaker.MatchmakerShard;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchmakerDeletedEventHandlerImpl implements EventHandler {

    final MatchmakerShard matchmakerShard;
    final TenantShard tenantShard;

    final DeleteMatchmakerCommandsOperation deleteMatchmakerCommandsOperation;
    final DeleteMatchmakerRequestsOperation deleteMatchmakerRequestsOperation;
    final DeleteMatchmakerMatchesOperation deleteMatchmakerMatchesOperation;
    final FindAndDeleteJobOperation findAndDeleteJobOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCHMAKER_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (MatchmakerDeletedEventBodyModel) event.getBody();
        final var matchmakerId = body.getId();

        return getMatchmaker(matchmakerId)
                .flatMap(matchmaker -> {
                    log.debug("Deleted, {}", matchmaker);

                    return deleteMatchmakerCommandsOperation.execute(matchmakerId)
                            .flatMap(voidItem -> deleteMatchmakerRequestsOperation.execute(matchmakerId))
                            .flatMap(voidItem -> deleteMatchmakerMatchesOperation.execute(matchmakerId))
                            .flatMap(voidItem -> findAndDeleteJobOperation.execute(matchmakerId, matchmakerId));
                })
                .replaceWithVoid();
    }

    Uni<MatchmakerModel> getMatchmaker(final Long matchmakerId) {
        final var request = new GetMatchmakerRequest(matchmakerId);
        return matchmakerShard.getService().execute(request)
                .map(GetMatchmakerResponse::getMatchmaker);
    }
}
