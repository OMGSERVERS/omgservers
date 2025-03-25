package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation;

import com.omgservers.schema.model.matchmakerState.MatchmakerStateDto;
import com.omgservers.schema.module.matchmaker.matchmakerState.GetMatchmakerStateRequest;
import com.omgservers.schema.module.matchmaker.matchmakerState.GetMatchmakerStateResponse;
import com.omgservers.service.service.task.impl.method.executeMatchmakerTask.dto.FetchMatchmakerResult;
import com.omgservers.service.shard.matchmaker.MatchmakerShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class FetchMatchmakerOperationImpl implements FetchMatchmakerOperation {

    final MatchmakerShard matchmakerShard;

    final GetTenantVersionConfigOperation getTenantVersionConfigOperation;

    @Override
    public Uni<FetchMatchmakerResult> execute(final Long matchmakerId) {
        return getTenantVersionConfigOperation.execute(matchmakerId)
                .flatMap(tenantVersionConfig -> getMatchmakerState(matchmakerId)
                        .map(matchmakerState -> new FetchMatchmakerResult(matchmakerId,
                                tenantVersionConfig,
                                matchmakerState)));
    }

    Uni<MatchmakerStateDto> getMatchmakerState(final Long matchmakerId) {
        final var request = new GetMatchmakerStateRequest(matchmakerId);
        return matchmakerShard.getService().execute(request)
                .map(GetMatchmakerStateResponse::getMatchmakerState);
    }
}
