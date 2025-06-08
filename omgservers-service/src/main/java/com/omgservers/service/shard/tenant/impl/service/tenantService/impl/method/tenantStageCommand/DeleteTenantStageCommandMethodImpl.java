package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStageCommand;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantStageCommand.DeleteTenantStageCommandRequest;
import com.omgservers.schema.shard.tenant.tenantStageCommand.DeleteTenantStageCommandResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantStageCommand.DeleteTenantStageCommandOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteTenantStageCommandMethodImpl implements DeleteTenantStageCommandMethod {

    final DeleteTenantStageCommandOperation deleteTenantStageCommandOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    @Override
    public Uni<DeleteTenantStageCommandResponse> execute(final ShardModel shardModel,
                                                         final DeleteTenantStageCommandRequest request) {
        log.debug("{}", request);

        final var tenantId = request.getTenantId();
        final var id = request.getId();

        return changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) -> deleteTenantStageCommandOperation
                                .execute(changeContext,
                                        sqlConnection,
                                        shardModel.slot(),
                                        tenantId,
                                        id))
                .map(ChangeContext::getResult)
                .map(DeleteTenantStageCommandResponse::new);
    }
}