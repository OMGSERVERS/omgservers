package com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.impl.method.deleteMatchmaker;

import com.omgservers.dto.matchmaker.DeleteMatchmakerShardedRequest;
import com.omgservers.dto.matchmaker.DeleteMatchmakerShardedResponse;
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
    public Uni<DeleteMatchmakerShardedResponse> deleteMatchmaker(DeleteMatchmakerShardedRequest request) {
        DeleteMatchmakerShardedRequest.validate(request);

        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeFunction(shardModel, id))
                .map(DeleteMatchmakerShardedResponse::new);
    }

    Uni<Boolean> changeFunction(ShardModel shardModel, Long id) {
        return changeWithContextOperation.changeWithContext((changeContext, sqlConnection) ->
                deleteMatchmakerOperation.deleteMatchmaker(changeContext, sqlConnection, shardModel.shard(), id));
    }
}
