package com.omgservers.service.module.root.impl.service.rootService.impl.method.rootEntityRef.syncRootEntityRef;

import com.omgservers.model.dto.root.rootEntityRef.SyncRootEntityRefRequest;
import com.omgservers.model.dto.root.rootEntityRef.SyncRootEntityRefResponse;
import com.omgservers.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.root.impl.operation.root.hasRoot.HasRootOperation;
import com.omgservers.service.module.root.impl.operation.rootEntityRef.upsertRootEntityRef.UpsertRootEntityRefOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
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
    final CheckShardOperation checkShardOperation;
    final HasRootOperation hasRootOperation;

    @Override
    public Uni<SyncRootEntityRefResponse> syncRootEntityRef(
            final SyncRootEntityRefRequest request) {
        log.debug("Sync root entity ref request, request={}", request);

        final var shardKey = request.getRequestShardKey();
        final var rootEntityRef = request.getRootEntityRef();
        final var rootId = rootEntityRef.getRootId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(shardKey))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (context, sqlConnection) -> hasRootOperation
                                            .hasRoot(sqlConnection, shard, rootId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertRootEntityRefOperation
                                                            .upsertRootEntityRef(
                                                                    context,
                                                                    sqlConnection,
                                                                    shard,
                                                                    rootEntityRef);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "root does not exist or was deleted, id=" + rootId);
                                                }
                                            }))
                            .map(ChangeContext::getResult);
                })
                .map(SyncRootEntityRefResponse::new);
    }
}
