package com.omgservers.service.operation.runtime;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.model.poolSeverContainer.PoolContainerLabel;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentResponse;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageResponse;
import com.omgservers.service.operation.alias.ViewTenantAliasesOperation;
import com.omgservers.service.operation.alias.ViewTenantProjectAliasesOperation;
import com.omgservers.service.operation.alias.ViewTenantStageAliasesOperation;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateRuntimeLabelsForContainerOperationImpl implements CreateRuntimeLabelsForContainerOperation {

    final TenantShard tenantShard;

    final ViewTenantProjectAliasesOperation viewTenantProjectAliasesOperation;
    final ViewTenantStageAliasesOperation viewTenantStageAliasesOperation;
    final ViewTenantAliasesOperation viewTenantAliasesOperation;

    @Override
    public Uni<Map<PoolContainerLabel, String>> execute(final RuntimeModel runtime) {
        final var tenantId = runtime.getTenantId();
        final var deploymentId = runtime.getDeploymentId();
        return getTenantDeployment(tenantId, deploymentId)
                .flatMap(tenantDeployment -> {
                    final var tenantStageId = tenantDeployment.getStageId();
                    final var tenantVersionId = tenantDeployment.getVersionId();
                    return getTenantStage(tenantId, tenantStageId)
                            .flatMap(tenantStage -> {
                                final var tenantProjectId = tenantStage.getProjectId();
                                return createLabels(tenantId, tenantProjectId, tenantStageId, tenantVersionId);
                            });
                });
    }

    Uni<TenantDeploymentModel> getTenantDeployment(final Long tenantId, final Long id) {
        final var request = new GetTenantDeploymentRequest(tenantId, id);
        return tenantShard.getService().getTenantDeployment(request)
                .map(GetTenantDeploymentResponse::getTenantDeployment);
    }

    Uni<TenantStageModel> getTenantStage(final Long tenantId, final Long id) {
        final var request = new GetTenantStageRequest(tenantId, id);
        return tenantShard.getService().getTenantStage(request)
                .map(GetTenantStageResponse::getTenantStage);
    }

    Uni<Map<PoolContainerLabel, String>> createLabels(final Long tenantId,
                                                      final Long tenantProjectId,
                                                      final Long tenantStageId,
                                                      final Long tenantVersionId) {
        final var labels = new HashMap<PoolContainerLabel, String>();
        return fillByTenantLabel(labels, tenantId)
                .flatMap(voidItem -> fillByTenantProjectLabel(labels, tenantId, tenantProjectId))
                .flatMap(voidItem -> fillByTenantStageLabel(labels, tenantId, tenantStageId))
                .flatMap(voidItem -> fillByVersionLabel(labels, tenantVersionId))
                .replaceWith(labels);
    }

    Uni<Void> fillByTenantLabel(final Map<PoolContainerLabel, String> labels,
                                final Long tenantId) {
        return viewTenantAliasesOperation.execute(tenantId)
                .invoke(tenantAliases -> tenantAliases.stream().max(Comparator.comparingLong(AliasModel::getId))
                        .ifPresent(item -> labels.put(PoolContainerLabel.OMG_TENANT, item.getValue())))
                .replaceWithVoid();
    }

    Uni<Void> fillByTenantProjectLabel(final Map<PoolContainerLabel, String> labels,
                                       final Long tenantId,
                                       final Long tenantProjectId) {
        return viewTenantProjectAliasesOperation.execute(tenantId, tenantProjectId)
                .invoke(tenantAliases -> tenantAliases.stream().max(Comparator.comparingLong(AliasModel::getId))
                        .ifPresent(alias -> labels.put(PoolContainerLabel.OMG_PROJECT, alias.getValue())))
                .replaceWithVoid();
    }

    Uni<Void> fillByTenantStageLabel(final Map<PoolContainerLabel, String> labels,
                                     final Long tenantId,
                                     final Long tenantStageId) {
        return viewTenantStageAliasesOperation.execute(tenantId, tenantStageId)
                .invoke(tenantAliases -> tenantAliases.stream().max(Comparator.comparingLong(AliasModel::getId))
                        .ifPresent(alias -> labels.put(PoolContainerLabel.OMG_STAGE, alias.getValue())))
                .replaceWithVoid();
    }

    Uni<Void> fillByVersionLabel(final Map<PoolContainerLabel, String> labels,
                                 final Long tenantVersionId) {
        return Uni.createFrom().voidItem()
                .invoke(voidItem -> labels.put(PoolContainerLabel.OMG_VERSION, tenantVersionId.toString()));
    }
}
