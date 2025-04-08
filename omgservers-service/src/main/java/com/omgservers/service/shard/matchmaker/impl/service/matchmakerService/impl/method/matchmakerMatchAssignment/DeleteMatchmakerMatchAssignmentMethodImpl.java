package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchAssignment;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.matchmaker.matchmakerMatchAssignment.DeleteMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.module.matchmaker.matchmakerMatchAssignment.DeleteMatchmakerMatchAssignmentResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatchAssignment.DeleteMatchmakerMatchAssignmentOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteMatchmakerMatchAssignmentMethodImpl implements DeleteMatchmakerMatchAssignmentMethod {

    final DeleteMatchmakerMatchAssignmentOperation deleteMatchmakerMatchAssignmentOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    @Override
    public Uni<DeleteMatchmakerMatchAssignmentResponse> execute(final ShardModel shardModel,
                                                                final DeleteMatchmakerMatchAssignmentRequest request) {
        log.trace("{}", request);

        final var matchmakerId = request.getMatchmakerId();
        final var id = request.getId();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deleteMatchmakerMatchAssignmentOperation.execute(changeContext,
                                sqlConnection,
                                shardModel.shard(),
                                matchmakerId, id))
                .map(ChangeContext::getResult)
                .map(DeleteMatchmakerMatchAssignmentResponse::new);
    }
}
