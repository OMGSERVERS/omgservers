package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchClient.deleteMatchmakerMatchClient;

import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchClientRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchClientResponse;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatchClient.deleteMatchmakerMatchClient.DeleteMatchmakerMatchClientOperation;
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
class DeleteMatchmakerMatchClientMethodImpl implements DeleteMatchmakerMatchClientMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteMatchmakerMatchClientOperation deleteMatchmakerMatchClientOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteMatchmakerMatchClientResponse> deleteMatchmakerMatchClient(DeleteMatchmakerMatchClientRequest request) {
        log.debug("Delete match client, request={}", request);

        final var matchmakerId = request.getMatchmakerId();
        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> deleteMatchmakerMatchClientOperation
                                        .deleteMatchmakerMatchClient(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                matchmakerId, id))
                        .map(ChangeContext::getResult))
                .map(DeleteMatchmakerMatchClientResponse::new);
    }
}
