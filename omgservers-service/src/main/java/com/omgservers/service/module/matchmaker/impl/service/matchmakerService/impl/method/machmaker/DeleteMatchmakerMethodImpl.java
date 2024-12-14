package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.machmaker;

import com.omgservers.schema.module.matchmaker.DeleteMatchmakerRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerResponse;
import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.service.module.matchmaker.impl.operation.matchmaker.DeleteMatchmakerOperation;
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
class DeleteMatchmakerMethodImpl implements DeleteMatchmakerMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteMatchmakerOperation deleteMatchmakerOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteMatchmakerResponse> deleteMatchmaker(DeleteMatchmakerRequest request) {
        log.trace("Requested, {}", request);

        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeFunction(shardModel, id))
                .map(DeleteMatchmakerResponse::new);
    }

    Uni<Boolean> changeFunction(ShardModel shardModel, Long id) {
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deleteMatchmakerOperation.execute(changeContext, sqlConnection, shardModel.shard(), id))
                .map(ChangeContext::getResult);
    }
}
