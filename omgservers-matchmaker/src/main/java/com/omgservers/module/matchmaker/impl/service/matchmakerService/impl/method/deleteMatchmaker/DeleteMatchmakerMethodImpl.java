package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.deleteMatchmaker;

import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.dto.matchmaker.DeleteMatchmakerRequest;
import com.omgservers.dto.matchmaker.DeleteMatchmakerResponse;
import com.omgservers.model.shard.ShardModel;
import com.omgservers.module.matchmaker.impl.operation.deleteMatchmaker.DeleteMatchmakerOperation;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
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
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteMatchmakerResponse> deleteMatchmaker(DeleteMatchmakerRequest request) {
        DeleteMatchmakerRequest.validate(request);

        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeFunction(shardModel, id))
                .map(DeleteMatchmakerResponse::new);
    }

    Uni<Boolean> changeFunction(ShardModel shardModel, Long id) {
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deleteMatchmakerOperation.deleteMatchmaker(changeContext, sqlConnection, shardModel.shard(), id))
                .map(ChangeContext::getResult);
    }
}
