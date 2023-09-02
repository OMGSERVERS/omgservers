package com.omgservers.test.cli;

import com.omgservers.dto.developer.CreateProjectDeveloperRequest;
import com.omgservers.dto.developer.CreateProjectDeveloperResponse;
import com.omgservers.dto.developer.CreateTokenDeveloperRequest;
import com.omgservers.dto.developer.CreateVersionDeveloperRequest;
import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.model.version.VersionSourceCodeModel;
import com.omgservers.test.operations.getConfigOperation.GetConfigOperation;
import com.omgservers.test.operations.getDeveloperClientOperation.DeveloperClientForAnonymousAccess;
import com.omgservers.test.operations.getDeveloperClientOperation.DeveloperClientForAuthenticatedAccess;
import com.omgservers.test.operations.getDeveloperClientOperation.GetDeveloperClientOperation;
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

    public Long createVersion(Long tenantId, Long stageId, VersionConfigModel versionConfig, VersionSourceCodeModel sourceCode) {
        final var createVersionDeveloperRequest = new CreateVersionDeveloperRequest(tenantId, stageId, versionConfig, sourceCode);
        final var createVersionDeveloperResponse = clientForAuthenticatedAccess.createVersion(TIMEOUT, rawToken, createVersionDeveloperRequest);
        return createVersionDeveloperResponse.getId();
    }
}
