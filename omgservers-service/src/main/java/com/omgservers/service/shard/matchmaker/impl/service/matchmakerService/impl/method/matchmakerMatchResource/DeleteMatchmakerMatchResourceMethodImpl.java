package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchResource;

import com.omgservers.schema.module.matchmaker.matchmakerMatchResource.DeleteMatchmakerMatchResourceRequest;
import com.omgservers.schema.module.matchmaker.matchmakerMatchResource.DeleteMatchmakerMatchResourceResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatchResource.DeleteMatchmakerMatchResourceOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteMatchmakerMatchResourceMethodImpl implements DeleteMatchmakerMatchResourceMethod {

    final DeleteMatchmakerMatchResourceOperation deleteMatchmakerMatchResourceOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteMatchmakerMatchResourceResponse> execute(final DeleteMatchmakerMatchResourceRequest request) {
        log.trace("{}", request);

        final var matchmakerId = request.getMatchmakerId();
        final var id = request.getId();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) ->
                                        deleteMatchmakerMatchResourceOperation.execute(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                matchmakerId,
                                                id))
                        .map(ChangeContext::getResult)
                )
                .map(DeleteMatchmakerMatchResourceResponse::new);
    }
}
