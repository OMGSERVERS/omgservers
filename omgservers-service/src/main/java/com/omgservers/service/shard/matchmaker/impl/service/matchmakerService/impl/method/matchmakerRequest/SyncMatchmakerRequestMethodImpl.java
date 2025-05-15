package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerRequest;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.matchmaker.matchmakerRequest.SyncMatchmakerRequestRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerRequest.SyncMatchmakerRequestResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmaker.VerifyMatchmakerExistsOperation;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerRequest.UpsertMatchmakerRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncMatchmakerRequestMethodImpl implements SyncMatchmakerRequestMethod {

    final VerifyMatchmakerExistsOperation verifyMatchmakerExistsOperation;
    final UpsertMatchmakerRequestOperation upsertRequestOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    @Override
    public Uni<SyncMatchmakerRequestResponse> execute(final ShardModel shardModel,
                                                      final SyncMatchmakerRequestRequest request) {
        log.debug("{}", request);

        final var requestModel = request.getMatchmakerRequest();
        final var matchmakerId = requestModel.getMatchmakerId();
        return changeWithContextOperation.<Boolean>changeWithContext(
                        (context, sqlConnection) -> verifyMatchmakerExistsOperation
                                .execute(sqlConnection, shardModel.slot(), matchmakerId)
                                .flatMap(exists -> {
                                    if (exists) {
                                        return upsertRequestOperation.execute(context,
                                                sqlConnection,
                                                shardModel.slot(),
                                                requestModel);
                                    } else {
                                        throw new ServerSideNotFoundException(
                                                ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                "matchmaker does not exist or was deleted, " +
                                                        "id=" + matchmakerId);
                                    }
                                })
                )
                .map(ChangeContext::getResult)
                .map(SyncMatchmakerRequestResponse::new);
    }
}
