package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.updateMatchmakerCommandsStatus;

import com.omgservers.dto.matchmaker.UpdateMatchmakerCommandsStatusRequest;
import com.omgservers.dto.matchmaker.UpdateMatchmakerCommandsStatusResponse;
import com.omgservers.module.matchmaker.impl.operation.updateMatchmakerCommandsStatusByIds.UpdateMatchmakerCommandStatusByIdsOperation;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpdateMatchmakerCommandsMethodImpl implements UpdateMatchmakerCommandsMethod {

    final UpdateMatchmakerCommandStatusByIdsOperation updateMatchmakerCommandStatusByIdsOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<UpdateMatchmakerCommandsStatusResponse> updateMatchmakerCommandsStatus(
            final UpdateMatchmakerCommandsStatusRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var matchmakerId = request.getMatchmakerId();
                    final var ids = request.getIds();
                    final var status = request.getStatus();
                    return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                                    updateMatchmakerCommandStatusByIdsOperation.updateMatchmakerCommandStatusByIds(
                                            changeContext,
                                            sqlConnection,
                                            shardModel.shard(),
                                            matchmakerId,
                                            ids,
                                            status))
                            .map(ChangeContext::getResult);
                })
                .map(UpdateMatchmakerCommandsStatusResponse::new);

    }
}
