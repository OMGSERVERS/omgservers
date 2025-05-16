package com.omgservers.service.server.task.impl.method.executeMatchmakerTask.operation;

import com.omgservers.schema.model.deployment.DeploymentModel;
import com.omgservers.schema.model.matchmakerState.MatchmakerStateDto;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import com.omgservers.schema.shard.deployment.deployment.GetDeploymentRequest;
import com.omgservers.schema.shard.deployment.deployment.GetDeploymentResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerState.GetMatchmakerStateRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerState.GetMatchmakerStateResponse;
import com.omgservers.schema.shard.tenant.tenantVersion.GetTenantVersionConfigRequest;
import com.omgservers.schema.shard.tenant.tenantVersion.GetTenantVersionConfigResponse;
import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.dto.FetchMatchmakerResult;
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
class FetchMatchmakerOperationImpl implements FetchMatchmakerOperation {

    final MatchmakerShard matchmakerShard;
    final DeploymentShard deploymentShard;
    final TenantShard tenantShard;

    @Override
    public Uni<FetchMatchmakerResult> execute(final Long matchmakerId) {
        return getMatchmakerState(matchmakerId)
                .flatMap(matchmakerState -> {
                    final var deploymentId = matchmakerState.getMatchmaker().getDeploymentId();
                    return getDeployment(deploymentId)
                            .flatMap(deployment -> {
                                final var tenantId = deployment.getTenantId();
                                final var tenantVersionId = deployment.getVersionId();
                                return getTenantVersionConfig(tenantId, tenantVersionId)
                                        .map(tenantVersionConfig -> new FetchMatchmakerResult(matchmakerId,
                                                tenantVersionConfig,
                                                deployment.getConfig(),
                                                matchmakerState));
                            });
                });
    }

    Uni<MatchmakerStateDto> getMatchmakerState(final Long matchmakerId) {
        final var request = new GetMatchmakerStateRequest(matchmakerId);
        return matchmakerShard.getService().execute(request)
                .map(GetMatchmakerStateResponse::getMatchmakerState);
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
