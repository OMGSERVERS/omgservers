package com.omgservers.platforms.integrationtest.cli;

import com.omgservers.application.module.developerModule.impl.service.developerHelpService.request.CreateProjectHelpRequest;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.request.CreateTokenHelpRequest;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.request.CreateVersionHelpRequest;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.request.GetVersionStatusHelpRequest;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.response.CreateProjectHelpResponse;
import com.omgservers.application.module.versionModule.model.VersionSourceCodeModel;
import com.omgservers.application.module.versionModule.model.VersionStatusEnum;
import com.omgservers.application.module.versionModule.model.VersionStageConfigModel;
import com.omgservers.platforms.integrationtest.operations.getConfigOperation.GetConfigOperation;
import com.omgservers.platforms.integrationtest.operations.getDeveloperClientOperation.DeveloperClientForAnonymousAccess;
import com.omgservers.platforms.integrationtest.operations.getDeveloperClientOperation.DeveloperClientForAuthenticatedAccess;
import com.omgservers.platforms.integrationtest.operations.getDeveloperClientOperation.GetDeveloperClientOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.UUID;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class DeveloperCli {
    private final int TIMEOUT = 10;

    final GetConfigOperation getConfigOperation;
    final GetDeveloperClientOperation getDeveloperClientOperation;

    DeveloperClientForAnonymousAccess clientForAnonymousAccess;
    DeveloperClientForAuthenticatedAccess clientForAuthenticatedAccess;
    String rawToken;

    public void createClient() {
        final var uri = getConfigOperation.getServers().get(0).externalAddress();
        createClient(uri);
    }

    public void createClient(URI uri) {
        clientForAnonymousAccess = getDeveloperClientOperation.getDeveloperClientForAnonymousAccess(uri);
        clientForAuthenticatedAccess = getDeveloperClientOperation.getDeveloperClientForAuthenticatedAccess(uri);
    }

    public DeveloperClientForAnonymousAccess getClientForAnonymousAccess() {
        return clientForAnonymousAccess;
    }

    public DeveloperClientForAuthenticatedAccess getClientForAuthenticatedAccess() {
        return clientForAuthenticatedAccess;
    }

    public String createToken(UUID user, String password) {
        final var createTokenDeveloperRequest = new CreateTokenHelpRequest(user, password);
        final var createTokenDeveloperResponse = clientForAnonymousAccess.createToken(TIMEOUT, createTokenDeveloperRequest);
        rawToken = createTokenDeveloperResponse.getRawToken();
        log.info("Token was created, user={}, {}", user, rawToken);
        return rawToken;
    }

    public CreateProjectHelpResponse createProject(UUID tenant, String title) {
        final var createProjectDeveloperRequest = new CreateProjectHelpRequest(tenant, title);
        final var response = clientForAuthenticatedAccess.createProject(TIMEOUT, rawToken, createProjectDeveloperRequest);
        return response;
    }

    public UUID createVersion(UUID tenant, UUID stage, VersionStageConfigModel stageConfig, VersionSourceCodeModel sourceCode) {
        final var createVersionDeveloperRequest = new CreateVersionHelpRequest(tenant, stage, stageConfig, sourceCode);
        final var createVersionDeveloperResponse = clientForAuthenticatedAccess.createVersion(TIMEOUT, rawToken, createVersionDeveloperRequest);
        return createVersionDeveloperResponse.getUuid();
    }

    public VersionStatusEnum getVersionStatus(UUID version) {
        final var response = clientForAuthenticatedAccess
                .getVersionStatus(TIMEOUT, rawToken, new GetVersionStatusHelpRequest(version));
        return response.getStatus();
    }
}
