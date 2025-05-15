package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchResource;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.SyncMatchmakerMatchResourceRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.SyncMatchmakerMatchResourceResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmaker.VerifyMatchmakerExistsOperation;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatchResource.UpsertMatchmakerMatchResourceOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncMatchmakerMatchResourceMethodImpl implements SyncMatchmakerMatchResourceMethod {

    final UpsertMatchmakerMatchResourceOperation upsertMatchmakerMatchResourceOperation;
    final VerifyMatchmakerExistsOperation verifyMatchmakerExistsOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    @Override
    public Uni<SyncMatchmakerMatchResourceResponse> execute(final ShardModel shardModel,
                                                            final SyncMatchmakerMatchResourceRequest request) {
        log.debug("{}", request);

        final var shardKey = request.getRequestShardKey();
        final var matchmakerMatchResource = request.getMatchmakerMatchResource();
        final var matchmakerId = matchmakerMatchResource.getMatchmakerId();

        return changeWithContextOperation.<Boolean>changeWithContext(
                        (context, sqlConnection) -> verifyMatchmakerExistsOperation
                                .execute(sqlConnection, shardModel.slot(), matchmakerId)
                                .flatMap(exists -> {
                                    if (exists) {
                                        return upsertMatchmakerMatchResourceOperation.execute(context,
                                                sqlConnection,
                                                shardModel.slot(),
                                                matchmakerMatchResource);
                                    } else {
                                        throw new ServerSideNotFoundException(
                                                ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                "matchmaker does not exist or was deleted, id=" +
                                                        matchmakerId);
                                    }
                                }))
                .map(ChangeContext::getResult)
                .map(SyncMatchmakerMatchResourceResponse::new);
    }
}
