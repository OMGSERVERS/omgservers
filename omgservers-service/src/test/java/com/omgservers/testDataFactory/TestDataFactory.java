package com.omgservers.testDataFactory;

import com.omgservers.schema.model.client.ClientModel;
import com.omgservers.schema.model.lobby.LobbyModel;
import com.omgservers.schema.model.lobbyRuntimeRef.LobbyRuntimeRefModel;
import com.omgservers.schema.model.player.PlayerModel;
import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.schema.model.tenantFilesArchive.TenantFilesArchiveModel;
import com.omgservers.schema.model.tenantImage.TenantImageModel;
import com.omgservers.schema.model.tenantLobbyRef.TenantLobbyRefModel;
import com.omgservers.schema.model.tenantLobbyRequest.TenantLobbyRequestModel;
import com.omgservers.schema.model.tenantMatchmakerRef.TenantMatchmakerRefModel;
import com.omgservers.schema.model.tenantMatchmakerRequest.TenantMatchmakerRequestModel;
import com.omgservers.schema.model.tenantPermission.TenantPermissionModel;
import com.omgservers.schema.model.tenantPermission.TenantPermissionQualifierEnum;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionModel;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
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

@Slf4j
@Getter
@ApplicationScoped
@AllArgsConstructor
public class TestDataFactory {

    final MatchmakerTestDataFactory matchmakerTestDataFactory;
    final RuntimeTestDataFactory runtimeTestDataFactory;
    final TenantTestDataFactory tenantTestDataFactory;
    final ClientTestDataFactory clientTestDataFactory;
    final LobbyTestDataFactory lobbyTestDataFactory;
    final RootTestDataFactory rootTestDataFactory;
    final PoolTestDataFactory poolTestDataFactory;
    final UserTestDataFactory userTestDataFactory;

    public DefaultTestData createDefaultTestData() {
        final var developerUser = getUserTestDataFactory().createDeveloperUser("password");

        final var tenant = tenantTestDataFactory.createTenant();
        final var tenantProjectManagementPermission = tenantTestDataFactory
                .createTenantPermission(tenant, developerUser, TenantPermissionQualifierEnum.PROJECT_MANAGER);
        final var tenantGettingDashboardPermission = tenantTestDataFactory
                .createTenantPermission(tenant, developerUser, TenantPermissionQualifierEnum.TENANT_VIEWER);

        final var tenantProject = tenantTestDataFactory.createTenantProject(tenant);
        final var tenantProjectStageManagementPermission = tenantTestDataFactory
                .createTenantProjectPermission(tenantProject, developerUser,
                        TenantProjectPermissionQualifierEnum.STAGE_MANAGER);
        final var tenantProjectVersionManagementPermission = tenantTestDataFactory
                .createTenantProjectPermission(tenantProject, developerUser,
                        TenantProjectPermissionQualifierEnum.VERSION_MANAGER);
        final var tenantProjectGettingDashboardPermission = tenantTestDataFactory
                .createTenantProjectPermission(tenantProject, developerUser,
                        TenantProjectPermissionQualifierEnum.PROJECT_VIEWER);

        final var tenantStage = tenantTestDataFactory.createStage(tenantProject);
        final var tenantStageDeploymentManagementPermission = tenantTestDataFactory
                .createTenantStagePermission(tenantStage, developerUser,
                        TenantStagePermissionQualifierEnum.DEPLOYMENT_MANAGER);
        final var tenantStageGettingDashboardPermission = tenantTestDataFactory
                .createTenantStagePermission(tenantStage, developerUser,
                        TenantStagePermissionQualifierEnum.STAGE_VIEWER);

        final var tenantVersion = tenantTestDataFactory.createTenantVersion(tenantProject);
        final var tenantFilesArchive = tenantTestDataFactory.createTenantFilesArchive(tenantVersion);
        final var tenantDeployment = tenantTestDataFactory.createTenantDeployment(tenantStage,
                tenantVersion);
        final var tenantImage = tenantTestDataFactory.createTenantImage(tenantVersion);

        final var tenantLobbyRequest = tenantTestDataFactory
                .createTenantLobbyRequest(tenantDeployment);
        final var tenantMatchmakerRequest = tenantTestDataFactory
                .createTenantMatchmakerRequest(tenantDeployment);

        final var lobby = lobbyTestDataFactory.createLobby(tenantDeployment);
        final var lobbyRuntime = runtimeTestDataFactory.createLobbyRuntime(tenant,
                tenantDeployment,
                lobby);
        final var lobbyRuntimeRef = lobbyTestDataFactory.createLobbyRuntimeRef(lobby,
                lobbyRuntime);

        final var tenantLobbyRef = tenantTestDataFactory
                .createTenantLobbyRef(tenantDeployment, lobby);

        final var matchmaker = getMatchmakerTestDataFactory().createMatchmaker(tenant,
                tenantDeployment);

        final var tenantMatchmakerRef = tenantTestDataFactory
                .createTenantMatchmakerRef(tenantDeployment, matchmaker);

        final var user = getUserTestDataFactory().createPlayerUser("password");
        final var player = getUserTestDataFactory().createUserPlayer(user, tenant, tenantStage);
        final var client = getClientTestDataFactory().createClient(player,
                tenant,
                tenantDeployment);

        final var matchmakerAssignment = getMatchmakerTestDataFactory()
                .createMatchmakerAssignment(matchmaker, client);

        return DefaultTestData.builder()
                .developerUser(developerUser)

                .tenant(tenant)
                .tenantProjectManagementPermission(tenantProjectManagementPermission)
                .tenantGettingDashboardPermission(tenantGettingDashboardPermission)

                .tenantProject(tenantProject)
                .tenantProjectStageManagementPermission(tenantProjectStageManagementPermission)
                .tenantProjectVersionManagementPermission(tenantProjectVersionManagementPermission)
                .tenantProjectGettingDashboardPermission(tenantProjectGettingDashboardPermission)

                .tenantStage(tenantStage)
                .tenantStageDeploymentManagementPermission(tenantStageDeploymentManagementPermission)
                .tenantStageGettingDashboardPermission(tenantStageGettingDashboardPermission)

                .tenantVersion(tenantVersion)
                .tenantFilesArchive(tenantFilesArchive)
                .tenantDeployment(tenantDeployment)
                .tenantImage(tenantImage)

                .tenantLobbyRequest(tenantLobbyRequest)
                .tenantMatchmakerRequest(tenantMatchmakerRequest)

                .tenantLobbyRef(tenantLobbyRef)
                .tenantMatchmakerRef(tenantMatchmakerRef)

                .lobby(lobby)
                .lobbyRuntime(lobbyRuntime)
                .lobbyRuntimeRef(lobbyRuntimeRef)

                .user(user)
                .player(player)
                .client(client)
                .build();
    }

    @Data
    @Builder
    static public class DefaultTestData {
        UserModel developerUser;

        TenantModel tenant;
        TenantPermissionModel tenantProjectManagementPermission;
        TenantPermissionModel tenantGettingDashboardPermission;

        TenantProjectModel tenantProject;
        TenantProjectPermissionModel tenantProjectStageManagementPermission;
        TenantProjectPermissionModel tenantProjectVersionManagementPermission;
        TenantProjectPermissionModel tenantProjectGettingDashboardPermission;

        TenantStageModel tenantStage;
        TenantStagePermissionModel tenantStageDeploymentManagementPermission;
        TenantStagePermissionModel tenantStageGettingDashboardPermission;

        TenantVersionModel tenantVersion;
        TenantFilesArchiveModel tenantFilesArchive;
        TenantDeploymentModel tenantDeployment;
        TenantImageModel tenantImage;

        TenantLobbyRequestModel tenantLobbyRequest;
        TenantMatchmakerRequestModel tenantMatchmakerRequest;

        TenantLobbyRefModel tenantLobbyRef;
        TenantMatchmakerRefModel tenantMatchmakerRef;

        LobbyModel lobby;
        RuntimeModel lobbyRuntime;
        LobbyRuntimeRefModel lobbyRuntimeRef;

        UserModel user;
        PlayerModel player;
        ClientModel client;
    }
}
