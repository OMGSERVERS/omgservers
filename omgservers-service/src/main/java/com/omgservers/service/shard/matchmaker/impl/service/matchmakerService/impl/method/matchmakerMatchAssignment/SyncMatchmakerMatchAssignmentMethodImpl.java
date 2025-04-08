package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchAssignment;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.matchmaker.matchmakerMatchAssignment.SyncMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.module.matchmaker.matchmakerMatchAssignment.SyncMatchmakerMatchAssignmentResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatchAssignment.UpsertMatchmakerMatchAssignmentOperation;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatchResource.VerifyMatchmakerMatchResourceExistsOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncMatchmakerMatchAssignmentMethodImpl implements SyncMatchmakerMatchAssignmentMethod {

    final VerifyMatchmakerMatchResourceExistsOperation verifyMatchmakerMatchResourceExistsOperation;
    final UpsertMatchmakerMatchAssignmentOperation upsertMatchmakerMatchAssignmentOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    @Override
    public Uni<SyncMatchmakerMatchAssignmentResponse> execute(final ShardModel shardModel,
                                                              final SyncMatchmakerMatchAssignmentRequest request) {
        log.trace("{}", request);

        final var matchmakerMatchAssignment = request.getMatchmakerMatchAssignment();
        final var matchmakerId = matchmakerMatchAssignment.getMatchmakerId();
        final var matchId = matchmakerMatchAssignment.getMatchId();

        return changeWithContextOperation.<Boolean>changeWithContext(
                        (context, sqlConnection) -> verifyMatchmakerMatchResourceExistsOperation
                                .execute(sqlConnection, shardModel.shard(), matchmakerId, matchId)
                                .flatMap(exists -> {
                                    if (exists) {
                                        return upsertMatchmakerMatchAssignmentOperation
                                                .execute(
                                                        context,
                                                        sqlConnection,
                                                        shardModel.shard(),
                                                        matchmakerMatchAssignment);
                                    } else {
                                        throw new ServerSideNotFoundException(
                                                ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                "match does not exist or was deleted, id=" + matchId);
                                    }
                                })
                )
                .map(ChangeContext::getResult)
                .map(SyncMatchmakerMatchAssignmentResponse::new);
    }
}
