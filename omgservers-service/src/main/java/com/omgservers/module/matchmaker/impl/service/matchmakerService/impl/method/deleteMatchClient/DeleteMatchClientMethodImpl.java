package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.deleteMatchClient;

import com.omgservers.dto.matchmaker.DeleteMatchClientRequest;
import com.omgservers.dto.matchmaker.DeleteMatchClientResponse;
import com.omgservers.module.matchmaker.impl.operation.deleteMatchClient.DeleteMatchClientOperation;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteMatchClientMethodImpl implements DeleteMatchClientMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteMatchClientOperation deleteMatchClientOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteMatchClientResponse> deleteMatchClient(DeleteMatchClientRequest request) {
        final var matchmakerId = request.getMatchmakerId();
        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> deleteMatchClientOperation
                                        .deleteMatchClient(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                matchmakerId, id))
                        .map(ChangeContext::getResult))
                .map(DeleteMatchClientResponse::new);
    }
}
