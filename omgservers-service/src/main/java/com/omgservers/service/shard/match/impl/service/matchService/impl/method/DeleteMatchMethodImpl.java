package com.omgservers.service.shard.match.impl.service.matchService.impl.method;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.match.DeleteMatchRequest;
import com.omgservers.schema.shard.match.DeleteMatchResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
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

    @Override
    public Uni<DeleteMatchResponse> execute(final ShardModel shardModel,
                                            final DeleteMatchRequest request) {
        log.debug("{}", request);

        final var id = request.getId();
        return changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) -> deleteMatchOperation.execute(changeContext,
                                sqlConnection,
                                shardModel.slot(),
                                id))
                .map(ChangeContext::getResult)
                .map(DeleteMatchResponse::new);
    }
}
