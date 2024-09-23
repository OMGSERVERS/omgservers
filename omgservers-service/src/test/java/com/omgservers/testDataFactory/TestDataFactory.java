package com.omgservers.testDataFactory;

import com.omgservers.schema.model.client.ClientModel;
import com.omgservers.schema.model.lobby.LobbyModel;
import com.omgservers.schema.model.lobbyRuntimeRef.LobbyRuntimeRefModel;
import com.omgservers.schema.model.player.PlayerModel;
import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.schema.model.tenantImageRef.TenantImageRefModel;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
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

    public TestData createTestData() {
        final var tenant = tenantTestDataFactory.createTenant();
        final var tenantProject = tenantTestDataFactory.createTenantProject(tenant);
        final var tenantStage = tenantTestDataFactory.createStage(tenantProject);
        final var tenantVersion = tenantTestDataFactory.createTenantVersion(tenantProject);
        final var tenantDeployment = tenantTestDataFactory.createTenantDeployment(tenantStage,
                tenantVersion);
        final var tenantImageRef = tenantTestDataFactory.createTenantImageRef(tenantVersion);

        final var lobby = lobbyTestDataFactory.createLobby(tenantDeployment);
        final var lobbyRuntime = runtimeTestDataFactory.createLobbyRuntime(tenant,
                tenantDeployment,
                lobby);
        final var lobbyRuntimeRef = lobbyTestDataFactory.createLobbyRuntimeRef(lobby,
                lobbyRuntime);

        final var matchmaker = getMatchmakerTestDataFactory().createMatchmaker(tenant,
                tenantDeployment);

        final var user = getUserTestDataFactory().createPlayerUser("password");
        final var player = getUserTestDataFactory().createUserPlayer(user, tenant, tenantStage);
        final var client = getClientTestDataFactory().createClient(player,
                tenant,
                tenantDeployment);

        final var matchmakerAssignment = getMatchmakerTestDataFactory()
                .createMatchmakerAssignment(matchmaker, client);

        return TestData.builder()
                .tenant(tenant)
                .tenantProject(tenantProject)
                .tenantStage(tenantStage)
                .tenantVersion(tenantVersion)
                .tenantDeployment(tenantDeployment)
                .tenantImageRef(tenantImageRef)

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
    static public class TestData {
        TenantModel tenant;
        TenantProjectModel tenantProject;
        TenantStageModel tenantStage;
        TenantVersionModel tenantVersion;
        TenantDeploymentModel tenantDeployment;
        TenantImageRefModel tenantImageRef;

        LobbyModel lobby;
        RuntimeModel lobbyRuntime;
        LobbyRuntimeRefModel lobbyRuntimeRef;

        UserModel user;
        PlayerModel player;
        ClientModel client;
    }
}
