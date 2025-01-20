package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerRequest;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerRequestRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerRequestResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmaker.HasMatchmakerOperation;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerRequest.UpsertMatchmakerRequestOperation;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncMatchmakerRequestMethodImpl implements SyncMatchmakerRequestMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertMatchmakerRequestOperation upsertRequestOperation;
    final HasMatchmakerOperation hasMatchmakerOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<SyncMatchmakerRequestResponse> execute(final SyncMatchmakerRequestRequest request) {
        log.trace("{}", request);

        final var shardKey = request.getRequestShardKey();
        final var requestModel = request.getMatchmakerRequest();
        final var matchmakerId = requestModel.getMatchmakerId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(shardKey))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (context, sqlConnection) -> hasMatchmakerOperation
                                            .execute(sqlConnection, shard, matchmakerId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertRequestOperation.execute(context,
                                                            sqlConnection,
                                                            shard,
                                                            requestModel);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "matchmaker does not exist or was deleted, " +
                                                                    "id=" + matchmakerId);
                                                }
                                            })
                            )
                            .map(ChangeContext::getResult);
                })
                .map(SyncMatchmakerRequestResponse::new);
    }
}
