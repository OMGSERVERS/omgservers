package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchResource;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.DeleteMatchmakerMatchResourceRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.DeleteMatchmakerMatchResourceResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
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

    @Override
    public Uni<DeleteMatchmakerMatchResourceResponse> execute(final ShardModel shardModel,
                                                              final DeleteMatchmakerMatchResourceRequest request) {
        log.debug("{}", request);

        final var matchmakerId = request.getMatchmakerId();
        final var id = request.getId();
        return changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) ->
                                deleteMatchmakerMatchResourceOperation.execute(changeContext,
                                        sqlConnection,
                                        shardModel.slot(),
                                        matchmakerId,
                                        id))
                .map(ChangeContext::getResult)
                .map(DeleteMatchmakerMatchResourceResponse::new);
    }
}
