package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchResource;

import com.omgservers.schema.module.matchmaker.matchmakerMatchResource.UpdateMatchmakerMatchResourceStatusRequest;
import com.omgservers.schema.module.matchmaker.matchmakerMatchResource.UpdateMatchmakerMatchResourceStatusResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatchResource.UpdateMatchmakerMatchResourceStatusOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpdateMatchmakerMatchResourceStatusMethodImpl implements UpdateMatchmakerMatchResourceStatusMethod {

    final UpdateMatchmakerMatchResourceStatusOperation updateMatchmakerMatchResourceStatusOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<UpdateMatchmakerMatchResourceStatusResponse> execute(
            final UpdateMatchmakerMatchResourceStatusRequest request) {
        log.trace("{}", request);

        final var matchmakerId = request.getMatchmakerId();
        final var matchId = request.getId();
        final var status = request.getStatus();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                            updateMatchmakerMatchResourceStatusOperation.execute(changeContext,
                                    sqlConnection,
                                    shard,
                                    matchmakerId,
                                    matchId,
                                    status
                            ));
                })
                .map(ChangeContext::getResult)
                .map(UpdateMatchmakerMatchResourceStatusResponse::new);
    }
}
