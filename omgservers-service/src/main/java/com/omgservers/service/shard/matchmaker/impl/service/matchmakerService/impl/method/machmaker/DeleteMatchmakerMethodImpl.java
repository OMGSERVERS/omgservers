package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.machmaker;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.matchmaker.matchmaker.DeleteMatchmakerRequest;
import com.omgservers.schema.shard.matchmaker.matchmaker.DeleteMatchmakerResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmaker.DeleteMatchmakerOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteMatchmakerMethodImpl implements DeleteMatchmakerMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteMatchmakerOperation deleteMatchmakerOperation;

    @Override
    public Uni<DeleteMatchmakerResponse> deleteMatchmaker(final ShardModel shardModel,
                                                          final DeleteMatchmakerRequest request) {
        log.debug("{}", request);

        final var id = request.getId();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deleteMatchmakerOperation.execute(changeContext, sqlConnection, shardModel.slot(), id))
                .map(ChangeContext::getResult)
                .map(DeleteMatchmakerResponse::new);
    }
}
