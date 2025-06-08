package com.omgservers.testDataFactory;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.model.client.ClientModel;
import com.omgservers.schema.model.clientRuntimeRef.ClientRuntimeRefModel;
import com.omgservers.schema.model.deployment.DeploymentModel;
import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceModel;
import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceModel;
import com.omgservers.schema.model.lobby.LobbyModel;
import com.omgservers.schema.model.matchmaker.MatchmakerModel;
import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceModel;
import com.omgservers.schema.model.matchmakerRequest.MatchmakerRequestModel;
import com.omgservers.schema.model.player.PlayerModel;
import com.omgservers.schema.model.pool.PoolModel;
import com.omgservers.schema.model.poolRequest.PoolRequestModel;
import com.omgservers.schema.model.poolServer.PoolServerModel;
import com.omgservers.schema.model.poolContainer.PoolContainerModel;
import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import com.omgservers.schema.model.runtimeMessage.RuntimeMessageModel;
import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.schema.model.tenantImage.TenantImageModel;
import com.omgservers.schema.model.tenantPermission.TenantPermissionModel;
import com.omgservers.schema.model.tenantPermission.TenantPermissionQualifierEnum;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionModel;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.model.tenantStageCommand.TenantStageCommandModel;
import com.omgservers.schema.model.tenantStageCommand.TenantStageCommandQualifierEnum;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionModel;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionQualifierEnum;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.model.user.UserModel;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@Getter
@ApplicationScoped
@AllArgsConstructor
public class TestDataFactory {

    final MatchmakerTestDataFactory matchmakerTestDataFactory;
    final DeploymentTestDataFactory deploymentTestDataFactory;
    final RuntimeTestDataFactory runtimeTestDataFactory;
    final TenantTestDataFactory tenantTestDataFactory;
    final ClientTestDataFactory clientTestDataFactory;
    final LobbyTestDataFactory lobbyTestDataFactory;
    final MatchTestDataFactory matchTestDataFactory;
    final AliasTestDataFactory aliasTestDataFactory;
    final PoolTestDataFactory poolTestDataFactory;
    final UserTestDataFactory userTestDataFactory;

    public DefaultTestData createDefaultTestData() {
        final var developerUser = getUserTestDataFactory().createDeveloperUser("password");

        final var tenant = tenantTestDataFactory.createTenant();
        final var tenantAlias = aliasTestDataFactory.createAlias(tenant,
                "tenant-" + UUID.randomUUID());

        final var tenantProjectManagerPermission = tenantTestDataFactory.createTenantPermission(tenant, developerUser,
                TenantPermissionQualifierEnum.PROJECT_MANAGER);
        final var tenantViewerPermission = tenantTestDataFactory.createTenantPermission(tenant, developerUser,
                TenantPermissionQualifierEnum.TENANT_VIEWER);

        final var tenantProject = tenantTestDataFactory.createTenantProject(tenant);
        final var tenantProjectStageManagerPermission =
                tenantTestDataFactory.createTenantProjectPermission(tenantProject, developerUser,
                        TenantProjectPermissionQualifierEnum.STAGE_MANAGER);
        final var tenantProjectVersionManagerPermission =
                tenantTestDataFactory.createTenantProjectPermission(tenantProject, developerUser,
                        TenantProjectPermissionQualifierEnum.VERSION_MANAGER);
        final var tenantProjectViewerPermission = tenantTestDataFactory
                .createTenantProjectPermission(tenantProject, developerUser,
                        TenantProjectPermissionQualifierEnum.PROJECT_VIEWER);

        final var tenantStage = tenantTestDataFactory.createStage(tenantProject);
        final var tenantStageDeploymentManagerPermission = tenantTestDataFactory
                .createTenantStagePermission(tenantStage, developerUser,
                        TenantStagePermissionQualifierEnum.DEPLOYMENT_MANAGER);
        final var tenantStageViewerPermission = tenantTestDataFactory
                .createTenantStagePermission(tenantStage, developerUser,
                        TenantStagePermissionQualifierEnum.STAGE_VIEWER);

        final var tenantVersion = tenantTestDataFactory.createTenantVersion(tenantProject);
        final var tenantImage = tenantTestDataFactory.createTenantImage(tenantVersion);

        final var deployment = deploymentTestDataFactory.createDeployment(tenantStage, tenantVersion);
        final var openDeploymentTenantStageCommand = tenantTestDataFactory.createTenantStageCommand(tenantStage,
                deployment,
                TenantStageCommandQualifierEnum.OPEN_DEPLOYMENT);
        final var deploymentLobbyResource = deploymentTestDataFactory.createDeploymentLobbyResource(deployment);
        final var deploymentMatchmakerResource =
                deploymentTestDataFactory.createDeploymentMatchmakerResource(deployment);

        final var lobby = lobbyTestDataFactory.createLobby(deploymentLobbyResource);
        final var lobbyRuntime = runtimeTestDataFactory.createLobbyRuntime(deployment, lobby);
        final var lobbyRuntimeCreatedRuntimeMessage =
                runtimeTestDataFactory.createRuntimeCreatedRuntimeMessage(lobbyRuntime);

        final var matchmaker = matchmakerTestDataFactory.createMatchmaker(deploymentMatchmakerResource);
        final var matchmakerMatchResource = matchmakerTestDataFactory.createMatchmakerMatchResource(matchmaker);

        final var match = matchTestDataFactory.createMatch(matchmakerMatchResource);
        final var matchRuntime = runtimeTestDataFactory.createMatchRuntime(deployment, match);

        final var user = getUserTestDataFactory().createPlayerUser("password");
        final var player = getUserTestDataFactory().createUserPlayer(user, tenant, tenantStage);
        final var client = getClientTestDataFactory().createClient(player,
                deployment);

        final var lobbyRuntimeAssignment = runtimeTestDataFactory.createRuntimeAssignment(lobbyRuntime, client);
        final var clientRuntimeRef = clientTestDataFactory.createClientRuntimeRef(client, lobbyRuntime);

        final var matchmakerRequest = matchmakerTestDataFactory.createMatchmakerRequest(matchmaker, client);

        final var matchmakerMatchAssignment = matchmakerTestDataFactory
                .createMatchmakerMatchAssignment(matchmakerMatchResource, matchmakerRequest, client);
        final var matchRuntimeAssignment = runtimeTestDataFactory
                .createRuntimeAssignment(matchRuntime, client);

        final var defaultPool = poolTestDataFactory.createDefaultPool();
        final var defaultPoolServer = poolTestDataFactory.createPoolServer(defaultPool);
        final var lobbyPoolRequest = poolTestDataFactory.createPoolRequest(defaultPool, lobbyRuntime);
        final var lobbyPoolContainer = poolTestDataFactory
                .createPoolContainer(defaultPoolServer, lobbyRuntime);

        return DefaultTestData.builder()
                .developerUser(developerUser)

                .tenant(tenant)
                .tenantAlias(tenantAlias)
                .tenantProjectManagerPermission(tenantProjectManagerPermission)
                .tenantViewerPermission(tenantViewerPermission)

                .tenantProject(tenantProject)
                .tenantProjectStageManagerPermission(tenantProjectStageManagerPermission)
                .tenantProjectVersionManagerPermission(tenantProjectVersionManagerPermission)
                .tenantProjectViewerPermission(tenantProjectViewerPermission)

                .tenantStage(tenantStage)
                .tenantStageDeploymentManagerPermission(tenantStageDeploymentManagerPermission)
                .tenantStageViewerPermission(tenantStageViewerPermission)
                .openDeploymentTenantStageCommand(openDeploymentTenantStageCommand)

                .tenantVersion(tenantVersion)
                .tenantImage(tenantImage)

                .deployment(deployment)
                .deploymentLobbyResource(deploymentLobbyResource)
                .deploymentMatchmakerResource(deploymentMatchmakerResource)

                .lobby(lobby)
                .lobbyRuntime(lobbyRuntime)
                .lobbyRuntimeCreatedRuntimeMessage(lobbyRuntimeCreatedRuntimeMessage)
                .lobbyRuntimeAssignment(lobbyRuntimeAssignment)

                .matchmaker(matchmaker)
                .matchmakerRequest(matchmakerRequest)
                .matchmakerMatchResource(matchmakerMatchResource)
                .matchmakerMatchAssignment(matchmakerMatchAssignment)

                .matchRuntime(matchRuntime)
                .matchRuntimeAssignment(matchRuntimeAssignment)

                .user(user)
                .player(player)
                .client(client)
                .clientRuntimeRef(clientRuntimeRef)

                .defaultPool(defaultPool)
                .defaultPoolServer(defaultPoolServer)
                .lobbyPoolRequest(lobbyPoolRequest)
                .lobbyPoolContainer(lobbyPoolContainer)

                .build();
    }

    @Data
    @Builder
    static public class DefaultTestData {
        UserModel developerUser;

        TenantModel tenant;
        AliasModel tenantAlias;
        TenantPermissionModel tenantProjectManagerPermission;
        TenantPermissionModel tenantViewerPermission;

        TenantProjectModel tenantProject;
        TenantProjectPermissionModel tenantProjectStageManagerPermission;
        TenantProjectPermissionModel tenantProjectVersionManagerPermission;
        TenantProjectPermissionModel tenantProjectViewerPermission;

        TenantStageModel tenantStage;
        TenantStagePermissionModel tenantStageDeploymentManagerPermission;
        TenantStagePermissionModel tenantStageViewerPermission;
        TenantStageCommandModel openDeploymentTenantStageCommand;

        TenantVersionModel tenantVersion;
        TenantImageModel tenantImage;

        DeploymentModel deployment;
        DeploymentLobbyResourceModel deploymentLobbyResource;
        DeploymentMatchmakerResourceModel deploymentMatchmakerResource;

        LobbyModel lobby;
        RuntimeModel lobbyRuntime;
        RuntimeMessageModel lobbyRuntimeCreatedRuntimeMessage;
        RuntimeAssignmentModel lobbyRuntimeAssignment;

        MatchmakerModel matchmaker;
        MatchmakerRequestModel matchmakerRequest;
        MatchmakerMatchResourceModel matchmakerMatchResource;
        MatchmakerMatchAssignmentModel matchmakerMatchAssignment;

        RuntimeModel matchRuntime;
        RuntimeAssignmentModel matchRuntimeAssignment;

        UserModel user;
        PlayerModel player;
        ClientModel client;
        ClientRuntimeRefModel clientRuntimeRef;

        PoolModel defaultPool;
        PoolServerModel defaultPoolServer;
        PoolRequestModel lobbyPoolRequest;
        PoolContainerModel lobbyPoolContainer;
    }
}
