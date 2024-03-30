package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.syncMatchmakerAssignment;

import com.omgservers.model.dto.matchmaker.SyncMatchmakerAssignmentRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerAssignmentResponse;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.matchmaker.impl.operation.matchmaker.hasMatchmaker.HasMatchmakerOperation;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerAssignment.upsertMatchmakerAssignment.UpsertMatchmakerAssignmentOperation;
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
class SyncMatchmakerAssignmentMethodImpl implements SyncMatchmakerAssignmentMethod {

    final UpsertMatchmakerAssignmentOperation upsertMatchmakerAssignmentOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final HasMatchmakerOperation hasMatchmakerOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<SyncMatchmakerAssignmentResponse> syncMatchmakerAssignment(
            final SyncMatchmakerAssignmentRequest request) {
        log.debug("Sync matchmaker assignment, request={}", request);

        final var matchmakerAssignment = request.getMatchmakerAssignment();
        final var matchmakerId = matchmakerAssignment.getMatchmakerId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> {
                            final var shard = shardModel.shard();
                            return changeWithContextOperation.<Boolean>changeWithContext(
                                            (changeContext, sqlConnection) -> hasMatchmakerOperation
                                                    .hasMatchmaker(sqlConnection, shard, matchmakerId)
                                                    .flatMap(has -> {
                                                        if (has) {
                                                            return upsertMatchmakerAssignmentOperation
                                                                    .upsertMatchmakerAssignment(changeContext,
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
