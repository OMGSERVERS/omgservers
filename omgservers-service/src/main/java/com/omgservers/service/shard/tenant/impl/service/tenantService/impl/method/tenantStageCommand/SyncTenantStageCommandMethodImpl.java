package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStageCommand;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantStageCommand.SyncTenantStageCommandRequest;
import com.omgservers.schema.shard.tenant.tenantStageCommand.SyncTenantStageCommandResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantStage.VerifyTenantStageExistsOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantStageCommand.UpsertTenantStageCommandOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncTenantStageCommandMethodImpl implements SyncTenantStageCommandMethod {

    final UpsertTenantStageCommandOperation upsertTenantStageCommandOperation;
    final VerifyTenantStageExistsOperation verifyTenantStageExistsOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncTenantStageCommandResponse> execute(final ShardModel shardModel,
                                                       final SyncTenantStageCommandRequest request) {
        log.debug("{}", request);

        final var command = request.getTenantStageCommand();
        final var tenantId = command.getTenantId();
        final var stageId = command.getStageId();

        return changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) ->
                                verifyTenantStageExistsOperation.execute(sqlConnection,
                                                shardModel.slot(),
                                                tenantId,
                                                stageId)
                                        .flatMap(exists -> {
                                            if (exists) {
                                                return upsertTenantStageCommandOperation.execute(
                                                        changeContext,
                                                        sqlConnection,
                                                        shardModel.slot(),
                                                        command);
                                            } else {
                                                throw new ServerSideNotFoundException(
                                                        ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                        "stage does not exist or was deleted, id=" + stageId);
                                            }
                                        })
                )
                .map(ChangeContext::getResult)
                .map(SyncTenantStageCommandResponse::new);
    }
}