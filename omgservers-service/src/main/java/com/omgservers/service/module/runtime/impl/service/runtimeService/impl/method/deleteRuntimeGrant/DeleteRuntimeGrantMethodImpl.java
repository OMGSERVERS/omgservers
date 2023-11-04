package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.deleteRuntimeGrant;

import com.omgservers.model.dto.runtime.DeleteRuntimeGrantRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeGrantResponse;
import com.omgservers.service.module.runtime.impl.operation.deleteRuntimeGrant.DeleteRuntimeGrantOperation;
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
class DeleteRuntimeGrantMethodImpl implements DeleteRuntimeGrantMethod {

    final DeleteRuntimeGrantOperation deleteRuntimeGrantOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteRuntimeGrantResponse> deleteRuntimeGrant(DeleteRuntimeGrantRequest request) {
        final var runtimeId = request.getRuntimeId();
        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) ->
                                        deleteRuntimeGrantOperation.deleteRuntimeGrant(
                                                changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                runtimeId,
                                                id))
                        .map(ChangeContext::getResult))
                .map(DeleteRuntimeGrantResponse::new);
    }
}
