package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerAssignment;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerAssignmentRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerAssignmentResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmaker.HasMatchmakerOperation;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerAssignment.UpsertMatchmakerAssignmentOperation;
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
class SyncMatchmakerAssignmentMethodImpl implements SyncMatchmakerAssignmentMethod {

    final UpsertMatchmakerAssignmentOperation upsertMatchmakerAssignmentOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final HasMatchmakerOperation hasMatchmakerOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<SyncMatchmakerAssignmentResponse> execute(
            final SyncMatchmakerAssignmentRequest request) {
        log.trace("{}", request);

        final var matchmakerAssignment = request.getMatchmakerAssignment();
        final var matchmakerId = matchmakerAssignment.getMatchmakerId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> {
                            final var shard = shardModel.shard();
                            return changeWithContextOperation.<Boolean>changeWithContext(
                                            (changeContext, sqlConnection) -> hasMatchmakerOperation
                                                    .execute(sqlConnection, shard, matchmakerId)
                                                    .flatMap(has -> {
                                                        if (has) {
                                                            return upsertMatchmakerAssignmentOperation
                                                                    .execute(changeContext,
                                                                            sqlConnection,
                                                                            shard,
                                                                            matchmakerAssignment);
                                                        } else {
                                                            throw new ServerSideNotFoundException(
                                                                    ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                                    "matchmaker does not exist or was deleted, " +
                                                                            "id=" + matchmakerId);
                                                        }
                                                    })
                                    )
                                    .map(ChangeContext::getResult);
                        }
                )
                .map(SyncMatchmakerAssignmentResponse::new);
    }
}
