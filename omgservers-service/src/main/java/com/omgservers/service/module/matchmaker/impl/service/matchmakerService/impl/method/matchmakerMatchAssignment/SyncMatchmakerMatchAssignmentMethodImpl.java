package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchAssignment;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerMatchAssignmentResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatch.HasMatchmakerMatchOperation;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatchAssignment.UpsertMatchmakerMatchAssignmentOperation;
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
class SyncMatchmakerMatchAssignmentMethodImpl implements SyncMatchmakerMatchAssignmentMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertMatchmakerMatchAssignmentOperation upsertMatchmakerMatchAssignmentOperation;
    final CheckShardOperation checkShardOperation;
    final HasMatchmakerMatchOperation hasMatchmakerMatchOperation;

    @Override
    public Uni<SyncMatchmakerMatchAssignmentResponse> execute(
            final SyncMatchmakerMatchAssignmentRequest request) {
        log.trace("{}", request);

        final var matchClient = request.getMatchmakerMatchAssignment();
        final var matchmakerId = matchClient.getMatchmakerId();
        final var matchId = matchClient.getMatchId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (context, sqlConnection) -> hasMatchmakerMatchOperation
                                            .execute(sqlConnection, shard, matchmakerId, matchId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertMatchmakerMatchAssignmentOperation
                                                            .execute(
                                                                    context,
                                                                    sqlConnection,
                                                                    shard,
                                                                    matchClient);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "match does not exist or was deleted, id=" + matchId);
                                                }
                                            })
                            )
                            .map(ChangeContext::getResult);
                })
                .map(SyncMatchmakerMatchAssignmentResponse::new);
    }
}
