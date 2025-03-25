package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchResource;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.matchmaker.matchmakerMatchResource.SyncMatchmakerMatchResourceRequest;
import com.omgservers.schema.module.matchmaker.matchmakerMatchResource.SyncMatchmakerMatchResourceResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
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
    final ChangeWithContextOperation changeWithContextOperation;
    final VerifyMatchmakerExistsOperation verifyMatchmakerExistsOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<SyncMatchmakerMatchResourceResponse> execute(final SyncMatchmakerMatchResourceRequest request) {
        log.trace("{}", request);

        final var shardKey = request.getRequestShardKey();
        final var matchmakerMatchResource = request.getMatchmakerMatchResource();
        final var matchmakerId = matchmakerMatchResource.getMatchmakerId();

        return checkShardOperation.checkShard(shardKey)
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (context, sqlConnection) -> verifyMatchmakerExistsOperation
                                            .execute(sqlConnection, shard, matchmakerId)
                                            .flatMap(exists -> {
                                                if (exists) {
                                                    return upsertMatchmakerMatchResourceOperation.execute(context,
                                                            sqlConnection,
                                                            shard,
                                                            matchmakerMatchResource);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "matchmaker does not exist or was deleted, id=" +
                                                                    matchmakerId);
                                                }
                                            }))
                            .map(ChangeContext::getResult);
                })
                .map(SyncMatchmakerMatchResourceResponse::new);
    }
}
