package com.omgservers.service.shard.root.impl.service.rootService.impl.method.rootEntityRef;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.root.rootEntityRef.SyncRootEntityRefRequest;
import com.omgservers.schema.module.root.rootEntityRef.SyncRootEntityRefResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.root.impl.operation.root.VerifyRootExistsOperation;
import com.omgservers.service.shard.root.impl.operation.rootEntityRef.UpsertRootEntityRefOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncRootEntityRefMethodImpl implements SyncRootEntityRefMethod {

    final UpsertRootEntityRefOperation upsertRootEntityRefOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final VerifyRootExistsOperation verifyRootExistsOperation;

    @Override
    public Uni<SyncRootEntityRefResponse> execute(final ShardModel shardModel,
                                                  final SyncRootEntityRefRequest request) {
        log.trace("{}", request);

        final var shardKey = request.getRequestShardKey();
        final var rootEntityRef = request.getRootEntityRef();
        final var rootId = rootEntityRef.getRootId();

        return changeWithContextOperation.<Boolean>changeWithContext((context, sqlConnection) ->
                        verifyRootExistsOperation.execute(sqlConnection, shardModel.shard(), rootId)
                                .flatMap(exists -> {
                                    if (exists) {
                                        return upsertRootEntityRefOperation
                                                .execute(
                                                        context,
                                                        sqlConnection,
                                                        shardModel.shard(),
                                                        rootEntityRef);
                                    } else {
                                        throw new ServerSideNotFoundException(
                                                ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                "root does not exist or was deleted, id=" + rootId);
                                    }
                                }))
                .map(ChangeContext::getResult)
                .map(SyncRootEntityRefResponse::new);
    }
}
