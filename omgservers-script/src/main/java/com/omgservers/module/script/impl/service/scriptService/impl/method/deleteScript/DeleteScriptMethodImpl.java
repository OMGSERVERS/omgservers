package com.omgservers.module.script.impl.service.scriptService.impl.method.deleteScript;

import com.omgservers.dto.script.DeleteScriptRequest;
import com.omgservers.dto.script.DeleteScriptResponse;
import com.omgservers.module.script.impl.operation.deleteScript.DeleteScriptOperation;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteScriptMethodImpl implements DeleteScriptMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteScriptOperation deleteScriptOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteScriptResponse> deleteScript(final DeleteScriptRequest request) {
        DeleteScriptRequest.validate(request);

        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel ->
                        changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                                        deleteScriptOperation.deleteScript(
                                                changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                id))
                                .map(ChangeContext::getResult))
                .map(DeleteScriptResponse::new);
    }
}
