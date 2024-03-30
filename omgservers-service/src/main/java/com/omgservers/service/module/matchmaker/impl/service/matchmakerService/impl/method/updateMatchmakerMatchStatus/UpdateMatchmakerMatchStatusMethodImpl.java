package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.updateMatchmakerMatchStatus;

import com.omgservers.model.dto.matchmaker.UpdateMatchmakerMatchStatusRequest;
import com.omgservers.model.dto.matchmaker.UpdateMatchmakerMatchStatusResponse;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatch.updateMatchmakerMatchStatus.UpdateMatchmakerMatchStatusOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpdateMatchmakerMatchStatusMethodImpl implements UpdateMatchmakerMatchStatusMethod {

    final UpdateMatchmakerMatchStatusOperation updateMatchmakerMatchStatusOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<UpdateMatchmakerMatchStatusResponse> updateMatchmakerMatchStatus(
            final UpdateMatchmakerMatchStatusRequest request) {
        log.debug("Update matchmaker match status, request={}", request);

        final var matchmakerId = request.getMatchmakerId();
        final var matchId = request.getMatchId();
        final var status = request.getStatus();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                            updateMatchmakerMatchStatusOperation.updateMatchmakerMatchStatus(changeContext,
                                    sqlConnection,
                                    shard,
                                    matchmakerId,
                                    matchId,
                                    status
                            ));
                })
                .map(ChangeContext::getResult)
                .map(UpdateMatchmakerMatchStatusResponse::new);
    }
}
