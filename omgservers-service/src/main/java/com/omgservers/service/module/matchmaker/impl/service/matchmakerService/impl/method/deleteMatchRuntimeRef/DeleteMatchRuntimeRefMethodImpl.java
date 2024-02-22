package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.deleteMatchRuntimeRef;

import com.omgservers.model.dto.matchmaker.DeleteMatchRuntimeRefRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchRuntimeRefResponse;
import com.omgservers.service.module.matchmaker.impl.operation.deleteMatchRuntimeRef.DeleteMatchRuntimeRefOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteMatchRuntimeRefMethodImpl implements DeleteMatchRuntimeRefMethod {

    final DeleteMatchRuntimeRefOperation deleteMatchRuntimeRefOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteMatchRuntimeRefResponse> deleteMatchRuntimeRef(final DeleteMatchRuntimeRefRequest request) {
        log.debug("Delete match runtime ref, request={}", request);

        final var matchmakerId = request.getMatchmakerId();
        final var matchId = request.getMatchId();
        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> deleteMatchRuntimeRefOperation
                                        .deleteMatchRuntimeRef(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                matchmakerId,
                                                matchId,
                                                id))
                        .map(ChangeContext::getResult))
                .map(DeleteMatchRuntimeRefResponse::new);
    }
}
