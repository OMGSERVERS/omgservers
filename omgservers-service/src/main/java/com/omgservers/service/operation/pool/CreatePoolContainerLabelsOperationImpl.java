package com.omgservers.service.operation.pool;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.model.deployment.DeploymentModel;
import com.omgservers.schema.model.poolContainer.PoolContainerLabel;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.model.runtime.RuntimeQualifierEnum;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageResponse;
import com.omgservers.service.operation.alias.ViewTenantAliasesOperation;
import com.omgservers.service.operation.alias.ViewTenantProjectAliasesOperation;
import com.omgservers.service.operation.alias.ViewTenantStageAliasesOperation;
import com.omgservers.service.shard.deployment.DeploymentShard;
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
class CreatePoolContainerLabelsOperationImpl implements CreatePoolContainerLabelsOperation {

    final DeploymentShard deploymentShard;
    final TenantShard tenantShard;

    final ViewTenantProjectAliasesOperation viewTenantProjectAliasesOperation;
    final ViewTenantStageAliasesOperation viewTenantStageAliasesOperation;
    final ViewTenantAliasesOperation viewTenantAliasesOperation;

    @Override
    public Uni<Map<PoolContainerLabel, String>> execute(final RuntimeModel runtime,
                                                        final DeploymentModel deployment) {
        final var tenantId = deployment.getTenantId();
        final var tenantStageId = deployment.getStageId();
        final var tenantVersionId = deployment.getVersionId();
        return getTenantStage(tenantId, tenantStageId)
                .flatMap(tenantStage -> {
                    final var tenantProjectId = tenantStage.getProjectId();
                    return createLabels(tenantId, tenantProjectId, tenantStageId, tenantVersionId,
                            runtime.getQualifier());
                });
    }

    Uni<TenantStageModel> getTenantStage(final Long tenantId, final Long id) {
        final var request = new GetTenantStageRequest(tenantId, id);
        return tenantShard.getService().execute(request)
                .map(GetTenantStageResponse::getTenantStage);
    }

    Uni<Map<PoolContainerLabel, String>> createLabels(final Long tenantId,
                                                      final Long tenantProjectId,
                                                      final Long tenantStageId,
                                                      final Long tenantVersionId,
                                                      final RuntimeQualifierEnum runtimeQualifier) {
        final var labels = new HashMap<PoolContainerLabel, String>();
        return fillByTenantLabel(labels, tenantId)
                .flatMap(voidItem -> fillByTenantProjectLabel(labels, tenantId, tenantProjectId))
                .flatMap(voidItem -> fillByTenantStageLabel(labels, tenantId, tenantStageId))
                .flatMap(voidItem -> fillByVersionLabel(labels, tenantVersionId))
                .flatMap(voidItem -> fillByQualifierLabel(labels, runtimeQualifier))
                .replaceWith(labels);
    }

    Uni<Void> fillByTenantLabel(final Map<PoolContainerLabel, String> labels,
                                final Long tenantId) {
        return viewTenantAliasesOperation.execute(tenantId)
                .invoke(tenantAliases -> tenantAliases.stream().max(Comparator.comparingLong(AliasModel::getId))
                        .ifPresentOrElse(item -> labels.put(PoolContainerLabel.TENANT, item.getValue()),
                                () -> labels.put(PoolContainerLabel.TENANT, tenantId.toString())))
                .replaceWithVoid();
    }

    Uni<Void> fillByTenantProjectLabel(final Map<PoolContainerLabel, String> labels,
                                       final Long tenantId,
                                       final Long tenantProjectId) {
        return viewTenantProjectAliasesOperation.execute(tenantId, tenantProjectId)
                .invoke(tenantAliases -> tenantAliases.stream().max(Comparator.comparingLong(AliasModel::getId))
                        .ifPresentOrElse(alias -> labels.put(PoolContainerLabel.PROJECT, alias.getValue()),
                                () -> labels.put(PoolContainerLabel.PROJECT, tenantProjectId.toString())))
                .replaceWithVoid();
    }

    Uni<Void> fillByTenantStageLabel(final Map<PoolContainerLabel, String> labels,
                                     final Long tenantId,
                                     final Long tenantStageId) {
        return viewTenantStageAliasesOperation.execute(tenantId, tenantStageId)
                .invoke(tenantAliases -> tenantAliases.stream().max(Comparator.comparingLong(AliasModel::getId))
                        .ifPresentOrElse(alias -> labels.put(PoolContainerLabel.STAGE, alias.getValue()),
                                () -> labels.put(PoolContainerLabel.PROJECT, tenantStageId.toString())))
                .replaceWithVoid();
    }

    Uni<Void> fillByVersionLabel(final Map<PoolContainerLabel, String> labels,
                                 final Long tenantVersionId) {
        return Uni.createFrom().voidItem()
                .invoke(voidItem -> labels.put(PoolContainerLabel.VERSION, tenantVersionId.toString()));
    }

    Uni<Void> fillByQualifierLabel(final Map<PoolContainerLabel, String> labels,
                                   final RuntimeQualifierEnum runtimeQualifier) {
        return Uni.createFrom().voidItem()
                .invoke(voidItem -> labels.put(PoolContainerLabel.QUALIFIER, runtimeQualifier.name()));
    }
}
