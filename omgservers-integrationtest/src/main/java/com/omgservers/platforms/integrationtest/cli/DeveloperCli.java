package com.omgservers.platforms.integrationtest.cli;

import com.omgservers.dto.developerModule.CreateProjectDeveloperRequest;
import com.omgservers.dto.developerModule.CreateProjectDeveloperResponse;
import com.omgservers.dto.developerModule.CreateTokenDeveloperRequest;
import com.omgservers.dto.developerModule.CreateVersionDeveloperRequest;
import com.omgservers.dto.developerModule.GetVersionStatusDeveloperRequest;
import com.omgservers.model.version.VersionSourceCodeModel;
import com.omgservers.model.version.VersionStageConfigModel;
import com.omgservers.model.version.VersionStatusEnum;
import com.omgservers.platforms.integrationtest.operations.getConfigOperation.GetConfigOperation;
import com.omgservers.platforms.integrationtest.operations.getDeveloperClientOperation.DeveloperClientForAnonymousAccess;
import com.omgservers.platforms.integrationtest.operations.getDeveloperClientOperation.DeveloperClientForAuthenticatedAccess;
import com.omgservers.platforms.integrationtest.operations.getDeveloperClientOperation.GetDeveloperClientOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

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

    public String createToken(Long userId, String password) {
        final var createTokenDeveloperRequest = new CreateTokenDeveloperRequest(userId, password);
        final var createTokenDeveloperResponse = clientForAnonymousAccess.createToken(TIMEOUT, createTokenDeveloperRequest);
        rawToken = createTokenDeveloperResponse.getRawToken();
        log.info("Token was created, userId={}, {}", userId, rawToken);
        return rawToken;
    }

    public CreateProjectDeveloperResponse createProject(Long tenantId) {
        final var createProjectDeveloperRequest = new CreateProjectDeveloperRequest(tenantId);
        final var response = clientForAuthenticatedAccess.createProject(TIMEOUT, rawToken, createProjectDeveloperRequest);
        return response;
    }

    public Long createVersion(Long tenantId, Long stageId, VersionStageConfigModel stageConfig, VersionSourceCodeModel sourceCode) {
        final var createVersionDeveloperRequest = new CreateVersionDeveloperRequest(tenantId, stageId, stageConfig, sourceCode);
        final var createVersionDeveloperResponse = clientForAuthenticatedAccess.createVersion(TIMEOUT, rawToken, createVersionDeveloperRequest);
        return createVersionDeveloperResponse.getId();
    }

    public VersionStatusEnum getVersionStatus(Long versionId) {
        final var response = clientForAuthenticatedAccess
                .getVersionStatus(TIMEOUT, rawToken, new GetVersionStatusDeveloperRequest(versionId));
        return response.getStatus();
    }
}
