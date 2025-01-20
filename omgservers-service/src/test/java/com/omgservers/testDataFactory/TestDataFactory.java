package com.omgservers.testDataFactory;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.model.client.ClientModel;
import com.omgservers.schema.model.clientMatchmakerRef.ClientMatchmakerRefModel;
import com.omgservers.schema.model.lobby.LobbyModel;
import com.omgservers.schema.model.lobbyRuntimeRef.LobbyRuntimeRefModel;
import com.omgservers.schema.model.matchmaker.MatchmakerModel;
import com.omgservers.schema.model.matchmakerAssignment.MatchmakerAssignmentModel;
import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import com.omgservers.schema.model.matchmakerMatchRuntimeRef.MatchmakerMatchRuntimeRefModel;
import com.omgservers.schema.model.player.PlayerModel;
import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.schema.model.queue.QueueModel;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
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

import java.util.UUID;

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
    final QueueTestDataFactory queueTestDataFactory;
    final AliasTestDataFactory aliasTestDataFactory;
    final RootTestDataFactory rootTestDataFactory;
    final PoolTestDataFactory poolTestDataFactory;
    final UserTestDataFactory userTestDataFactory;

    public DefaultTestData createDefaultTestData() {
        final var developerUser = getUserTestDataFactory().createDeveloperUser("password");

        final var tenant = tenantTestDataFactory.createTenant();
        final var tenantAlias = aliasTestDataFactory.createAlias(tenant,
                "tenant-" + UUID.randomUUID());

        final var tenantProjectManagerPermission = tenantTestDataFactory
                .createTenantPermission(tenant, developerUser, TenantPermissionQualifierEnum.PROJECT_MANAGER);
        final var tenantViewerPermission = tenantTestDataFactory
                .createTenantPermission(tenant, developerUser, TenantPermissionQualifierEnum.TENANT_VIEWER);

        final var tenantProject = tenantTestDataFactory.createTenantProject(tenant);
        final var tenantProjectStageManagerPermission = tenantTestDataFactory
                .createTenantProjectPermission(tenantProject, developerUser,
                        TenantProjectPermissionQualifierEnum.STAGE_MANAGER);
        final var tenantProjectVersionManagerPermission = tenantTestDataFactory
                .createTenantProjectPermission(tenantProject, developerUser,
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

        final var matchmaker = matchmakerTestDataFactory.createMatchmaker(tenant,
                tenantDeployment);

        final var matchmakerMatch = matchmakerTestDataFactory.createMatchmakerMatch(matchmaker);

        final var matchRuntime = runtimeTestDataFactory
                .createMatchRuntime(tenant, tenantDeployment, matchmakerMatch);
        final var matchmakerMatchRuntimeRef = matchmakerTestDataFactory
                .createMatchmakerMatchRuntimeRef(matchmakerMatch, matchRuntime);

        final var tenantMatchmakerRef = tenantTestDataFactory
                .createTenantMatchmakerRef(tenantDeployment, matchmaker);

        final var queue = queueTestDataFactory.createQueue(tenantDeployment);

        final var user = getUserTestDataFactory().createPlayerUser("password");
        final var player = getUserTestDataFactory().createUserPlayer(user, tenant, tenantStage);
        final var client = getClientTestDataFactory().createClient(player,
                tenant,
                tenantDeployment);

        final var matchmakerAssignment = getMatchmakerTestDataFactory()
                .createMatchmakerAssignment(matchmaker, client);

        final var matchmakerMatchAssignment = matchmakerTestDataFactory
                .createMatchmakerMatchAssignment(matchmakerMatch, client);
        final var matchRuntimeAssignment = runtimeTestDataFactory
                .createRuntimeAssignment(matchRuntime, client);

        final var clientMatchmakerRef = getClientTestDataFactory()
                .createClientMatchmakerRef(client, matchmaker);

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

                .matchmaker(matchmaker)
                .matchmakerMatch(matchmakerMatch)
                .matchmakerAssignment(matchmakerAssignment)
                .matchmakerMatchRuntimeRef(matchmakerMatchRuntimeRef)
                .matchmakerMatchAssignment(matchmakerMatchAssignment)

                .matchRuntime(matchRuntime)
                .matchRuntimeAssignment(matchRuntimeAssignment)

                .queue(queue)

                .user(user)
                .player(player)
                .client(client)
                .clientMatchmakerRef(clientMatchmakerRef)

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

        MatchmakerModel matchmaker;
        MatchmakerMatchModel matchmakerMatch;
        MatchmakerAssignmentModel matchmakerAssignment;
        MatchmakerMatchRuntimeRefModel matchmakerMatchRuntimeRef;
        MatchmakerMatchAssignmentModel matchmakerMatchAssignment;

        RuntimeModel matchRuntime;
        RuntimeAssignmentModel matchRuntimeAssignment;

        QueueModel queue;

        UserModel user;
        PlayerModel player;
        ClientModel client;
        ClientMatchmakerRefModel clientMatchmakerRef;
    }
}
