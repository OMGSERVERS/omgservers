package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerRequest.syncMatchmakerRequest;

import com.omgservers.schema.module.matchmaker.SyncMatchmakerRequestRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerRequestResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.matchmaker.impl.operation.matchmaker.hasMatchmaker.HasMatchmakerOperation;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerRequest.upsertMatchmakerRequest.UpsertMatchmakerRequestOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
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
    public Uni<SyncMatchmakerRequestResponse> syncMatchmakerRequest(final SyncMatchmakerRequestRequest request) {
        log.debug("Sync matchmaker request, request={}", request);

        final var shardKey = request.getRequestShardKey();
        final var requestModel = request.getMatchmakerRequest();
        final var matchmakerId = requestModel.getMatchmakerId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(shardKey))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (context, sqlConnection) -> hasMatchmakerOperation
                                            .hasMatchmaker(sqlConnection, shard, matchmakerId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertRequestOperation.upsertMatchmakerRequest(context,
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
