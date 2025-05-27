package com.omgservers.testDataFactory;

import com.omgservers.schema.model.deployment.DeploymentConfigDto;
import com.omgservers.schema.model.deployment.DeploymentModel;
import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceConfigDto;
import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceModel;
import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceStatusEnum;
import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceConfigDto;
import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceModel;
import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceStatusEnum;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.shard.deployment.deployment.SyncDeploymentRequest;
import com.omgservers.schema.shard.deployment.deploymentLobbyResource.SyncDeploymentLobbyResourceRequest;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerResource.SyncDeploymentMatchmakerResourceRequest;
import com.omgservers.service.factory.deployment.DeploymentLobbyResourceModelFactory;
import com.omgservers.service.factory.deployment.DeploymentMatchmakerResourceModelFactory;
import com.omgservers.service.factory.deployment.DeploymentModelFactory;
import com.omgservers.service.shard.deployment.service.testInterface.DeploymentServiceTestInterface;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class DeploymentTestDataFactory {

    final DeploymentServiceTestInterface deploymentService;

    final DeploymentMatchmakerResourceModelFactory deploymentMatchmakerResourceModelFactory;
    final DeploymentLobbyResourceModelFactory deploymentLobbyResourceModelFactory;
    final DeploymentModelFactory deploymentModelFactory;

    public DeploymentModel createDeployment(final TenantStageModel tenantStage,
                                            final TenantVersionModel tenantVersion) {
        final var tenantId = tenantVersion.getTenantId();
        final var tenantStageId = tenantStage.getId();
        final var tenantVersionId = tenantVersion.getId();
        final var tenantDeployment = deploymentModelFactory.create(tenantId,
                tenantStageId,
                tenantVersionId,
                new DeploymentConfigDto());
        final var request = new SyncDeploymentRequest(tenantDeployment);
        deploymentService.execute(request);
        return tenantDeployment;
    }

    public DeploymentLobbyResourceModel createDeploymentLobbyResource(final DeploymentModel deployment) {
        final var deploymentId = deployment.getId();
        final var deploymentLobbyResource = deploymentLobbyResourceModelFactory.create(deploymentId,
                new DeploymentLobbyResourceConfigDto());
        deploymentLobbyResource.setStatus(DeploymentLobbyResourceStatusEnum.CREATED);
        final var request = new SyncDeploymentLobbyResourceRequest(deploymentLobbyResource);
        deploymentService.execute(request);
        return deploymentLobbyResource;
    }

    public DeploymentMatchmakerResourceModel createDeploymentMatchmakerResource(final DeploymentModel deployment) {
        final var deploymentId = deployment.getId();
        final var deploymentMatchmakerResource = deploymentMatchmakerResourceModelFactory.create(deploymentId,
                new DeploymentMatchmakerResourceConfigDto());
        deploymentMatchmakerResource.setStatus(DeploymentMatchmakerResourceStatusEnum.CREATED);
        final var request = new SyncDeploymentMatchmakerResourceRequest(deploymentMatchmakerResource);
        deploymentService.execute(request);
        return deploymentMatchmakerResource;
    }

}
