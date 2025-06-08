package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStageState;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceModel;
import com.omgservers.schema.model.tenantStageChangeOfState.TenantStageDeploymentResourceToUpdateStatusDto;
import com.omgservers.schema.shard.tenant.tenantStageState.UpdateTenantStageStateRequest;
import com.omgservers.schema.shard.tenant.tenantStageState.UpdateTenantStageStateResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantDeploymentResource.DeleteTenantDeploymentResourceOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantDeploymentResource.UpdateTenantDeploymentResourceStatusOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantStageCommand.DeleteTenantStageCommandOperation;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpdateTenantStageStateMethodImpl implements UpdateTenantStageStateMethod {

    final UpdateTenantDeploymentResourceStatusOperation updateTenantDeploymentResourceStatusOperation;
    final DeleteTenantDeploymentResourceOperation deleteTenantDeploymentResourceOperation;
    final DeleteTenantStageCommandOperation deleteTenantStageCommandOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    @Override
    public Uni<UpdateTenantStageStateResponse> execute(final ShardModel shardModel,
                                                       final UpdateTenantStageStateRequest request) {
        log.debug("{}", request);

        final var slot = shardModel.slot();
        final var tenantId = request.getTenantId();
        final var tenantStageId = request.getTenantStageId();
        final var tenantStageChangeOfState = request.getTenantStageChangeOfState();
        return changeWithContextOperation.<Void>changeWithContext(
                        (changeContext, sqlConnection) ->
                                deleteTenantStageCommands(changeContext,
                                        sqlConnection,
                                        slot,
                                        tenantId,
                                        tenantStageId,
                                        tenantStageChangeOfState.getTenantStageCommandsToDelete())
                                        .flatMap(voidItem -> deleteTenantDeploymentResources(changeContext,
                                                sqlConnection,
                                                slot,
                                                tenantId,
                                                tenantStageId,
                                                tenantStageChangeOfState.getTenantDeploymentResourcesToDelete()))
                                        .flatMap(voidItem -> updateTenantDeploymentResourcesStatus(changeContext,
                                                sqlConnection,
                                                slot,
                                                tenantId,
                                                tenantStageId,
                                                tenantStageChangeOfState.getTenantDeploymentResourcesToUpdateStatus()))
                )
                .replaceWith(Boolean.TRUE)
                .map(UpdateTenantStageStateResponse::new);
    }

    Uni<Void> deleteTenantStageCommands(final ChangeContext<?> changeContext,
                                        final SqlConnection sqlConnection,
                                        final int slot,
                                        final Long tenantId,
                                        final Long tenantStageId,
                                        final List<Long> tenantStageCommands) {
        return Multi.createFrom().iterable(tenantStageCommands)
                .onItem().transformToUniAndConcatenate(tenantStageCommandId ->
                        deleteTenantStageCommandOperation.execute(
                                changeContext,
                                sqlConnection,
                                slot,
                                tenantId,
                                tenantStageCommandId))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> deleteTenantDeploymentResources(final ChangeContext<?> changeContext,
                                              final SqlConnection sqlConnection,
                                              final int slot,
                                              final Long tenantId,
                                              final Long tenantStageId,
                                              final List<TenantDeploymentResourceModel> tenantDeploymentResources) {
        return Multi.createFrom().iterable(tenantDeploymentResources)
                .onItem().transformToUniAndConcatenate(tenantDeploymentResource ->
                        deleteTenantDeploymentResourceOperation.execute(
                                changeContext,
                                sqlConnection,
                                slot,
                                tenantId,
                                tenantDeploymentResource.getId()))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> updateTenantDeploymentResourcesStatus(final ChangeContext<?> changeContext,
                                                    final SqlConnection sqlConnection,
                                                    final int slot,
                                                    final Long tenantId,
                                                    final Long tenantStageId,
                                                    final List<TenantStageDeploymentResourceToUpdateStatusDto> tenantStageDeployments) {
        return Multi.createFrom().iterable(tenantStageDeployments)
                .onItem().transformToUniAndConcatenate(tenantStageDeployment ->
                        updateTenantDeploymentResourceStatusOperation.execute(
                                changeContext,
                                sqlConnection,
                                slot,
                                tenantId,
                                tenantStageDeployment.id(),
                                tenantStageDeployment.status()))
                .collect().asList()
                .replaceWithVoid();
    }
}
