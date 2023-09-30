package com.omgservers.module.script.impl.service.scriptService.impl.method.syncScript;

import com.omgservers.dto.script.SyncScriptRequest;
import com.omgservers.dto.script.SyncScriptResponse;
import com.omgservers.module.script.impl.operation.upsertScript.UpsertScriptOperation;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncScriptMethodImpl implements SyncScriptMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertScriptOperation upsertScriptOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncScriptResponse> syncScript(SyncScriptRequest request) {
        final var script = request.getScript();

        log.info("Sync script, type={}, id={}, versionId={}",
                script.getType(), script.getId(), script.getVersionId());

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel ->
                        changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                                        upsertScriptOperation.upsertScript(
                                                changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                script))
                                .map(ChangeContext::getResult))
                .map(SyncScriptResponse::new);
    }
}
