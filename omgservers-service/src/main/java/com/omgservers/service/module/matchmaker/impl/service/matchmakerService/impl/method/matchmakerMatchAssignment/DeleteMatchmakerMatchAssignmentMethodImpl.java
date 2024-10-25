package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchAssignment;

import com.omgservers.schema.module.matchmaker.DeleteMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerMatchAssignmentResponse;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatchAssignment.DeleteMatchmakerMatchAssignmentOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteMatchmakerMatchAssignmentMethodImpl implements DeleteMatchmakerMatchAssignmentMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteMatchmakerMatchAssignmentOperation deleteMatchmakerMatchAssignmentOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteMatchmakerMatchAssignmentResponse> execute(
            DeleteMatchmakerMatchAssignmentRequest request) {
        log.debug("Requested, {}", request);

        final var matchmakerId = request.getMatchmakerId();
        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> deleteMatchmakerMatchAssignmentOperation
                                        .execute(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                matchmakerId, id))
                        .map(ChangeContext::getResult))
                .map(DeleteMatchmakerMatchAssignmentResponse::new);
    }
}
