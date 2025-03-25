package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation;

import com.omgservers.schema.model.deployment.DeploymentModel;
import com.omgservers.schema.model.matchmaker.MatchmakerModel;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import com.omgservers.schema.module.deployment.deployment.GetDeploymentRequest;
import com.omgservers.schema.module.deployment.deployment.GetDeploymentResponse;
import com.omgservers.schema.module.matchmaker.matchmaker.GetMatchmakerRequest;
import com.omgservers.schema.module.matchmaker.matchmaker.GetMatchmakerResponse;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionConfigRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionConfigResponse;
import com.omgservers.service.shard.deployment.DeploymentShard;
import com.omgservers.service.shard.matchmaker.MatchmakerShard;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class GetTenantVersionConfigOperationImpl implements GetTenantVersionConfigOperation {

    final MatchmakerShard matchmakerShard;
    final DeploymentShard deploymentShard;
    final TenantShard tenantShard;

    @Override
    public Uni<TenantVersionConfigDto> execute(final Long matchmakerId) {
        return getMatchmaker(matchmakerId)
                .flatMap(matchmaker -> {
                    final var deploymentId = matchmaker.getDeploymentId();
                    return getDeployment(deploymentId)
                            .flatMap(deployment -> {
                                final var tenantId = deployment.getTenantId();
                                final var tenantVersionId = deployment.getVersionId();
                                return getTenantVersionConfig(tenantId, tenantVersionId);
                            });
                });
    }

    Uni<MatchmakerModel> getMatchmaker(final Long matchmakerId) {
        final var request = new GetMatchmakerRequest(matchmakerId);
        return matchmakerShard.getService().execute(request)
                .map(GetMatchmakerResponse::getMatchmaker);
    }

    Uni<DeploymentModel> getDeployment(final Long deploymentId) {
        final var request = new GetDeploymentRequest(deploymentId);
        return deploymentShard.getService().execute(request)
                .map(GetDeploymentResponse::getDeployment);
    }

    Uni<TenantVersionConfigDto> getTenantVersionConfig(final Long tenantId, final Long tenantVersionId) {
        final var request = new GetTenantVersionConfigRequest(tenantId, tenantVersionId);
        return tenantShard.getService().execute(request)
                .map(GetTenantVersionConfigResponse::getTenantVersionConfig);
    }
}
