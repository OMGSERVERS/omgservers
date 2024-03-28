package com.omgservers.service.module.server.impl.service.serverService.impl.method.deleteServer;

import com.omgservers.model.dto.server.DeleteServerRequest;
import com.omgservers.model.dto.server.DeleteServerResponse;
import com.omgservers.service.module.server.impl.operation.deleteServer.DeleteServerOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteServerMethodImpl implements DeleteServerMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteServerOperation deleteServerOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<DeleteServerResponse> deleteServer(final DeleteServerRequest request) {
        log.debug("Delete server, request={}", request);

        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> deleteServerOperation
                                        .deleteServer(changeContext, sqlConnection, shardModel.shard(), id))
                        .map(ChangeContext::getResult))
                .map(DeleteServerResponse::new);
    }
}
