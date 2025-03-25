package com.omgservers.service.shard.match.impl.service.matchService.impl.method;

import com.omgservers.schema.module.match.DeleteMatchRequest;
import com.omgservers.schema.module.match.DeleteMatchResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import com.omgservers.service.shard.match.impl.operation.match.DeleteMatchOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteMatchMethodImpl implements DeleteMatchMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteMatchOperation deleteMatchOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteMatchResponse> execute(final DeleteMatchRequest request) {
        log.trace("{}", request);

        final var id = request.getId();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> deleteMatchOperation.execute(changeContext,
                                        sqlConnection,
                                        shardModel.shard(),
                                        id))
                        .map(ChangeContext::getResult))
                .map(DeleteMatchResponse::new);
    }
}
