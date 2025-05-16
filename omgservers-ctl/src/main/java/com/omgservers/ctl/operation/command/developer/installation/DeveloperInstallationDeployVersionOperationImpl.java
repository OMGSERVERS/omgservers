package com.omgservers.ctl.operation.command.developer.installation;

import com.omgservers.ctl.configuration.LocalConfiguration;
import com.omgservers.ctl.dto.key.KeyEnum;
import com.omgservers.ctl.operation.client.CreateDeveloperAnonymousClientOperation;
import com.omgservers.ctl.operation.client.CreateDeveloperClientOperation;
import com.omgservers.ctl.operation.docker.CreateDockerDaemonClientOperation;
import com.omgservers.ctl.operation.service.DeployVersionOperation;
import com.omgservers.ctl.operation.wal.AppendResultMapOperation;
import com.omgservers.ctl.operation.wal.GetWalOperation;
import com.omgservers.ctl.operation.wal.installation.FindInstallationDetailsOperation;
import com.omgservers.schema.entrypoint.developer.CreateTokenDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTokenDeveloperResponse;
import com.omgservers.schema.model.deployment.DeploymentConfigDto;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.Map;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeveloperInstallationDeployVersionOperationImpl implements DeveloperInstallationDeployVersionOperation {

    final CreateDeveloperAnonymousClientOperation createDeveloperAnonymousClientOperation;
    final CreateDockerDaemonClientOperation createDockerDaemonClientOperation;
    final FindInstallationDetailsOperation findInstallationDetailsOperation;
    final CreateDeveloperClientOperation createDeveloperClientOperation;
    final AppendResultMapOperation appendResultMapOperation;
    final DeployVersionOperation deployVersionOperation;
    final GetWalOperation getWalOperation;

    @Override
    public void execute(final String developer,
                        final String password,
                        final String tenant,
                        final String project,
                        final String stage,
                        final String image,
                        final TenantVersionConfigDto versionConfig,
                        final DeploymentConfigDto deploymentConfig,
                        final String installation) {
        final var wal = getWalOperation.execute();
        final var path = wal.getPath();

        final var installationDetails = findInstallationDetailsOperation.execute(wal, installation);
        final var serviceUri = installationDetails.getApi();
        final var serviceRegistry = installationDetails.getRegistry();

        final var developerToken = createDeveloperToken(serviceUri, developer, password);
        final var developerClient = createDeveloperClientOperation.execute(serviceUri, developerToken);

        final var dockerDaemonUri = LocalConfiguration.DOCKER_URI;
        final var dockerClient = createDockerDaemonClientOperation.execute(dockerDaemonUri,
                serviceRegistry,
                developer,
                password);

        final var result = deployVersionOperation.execute(developerClient,
                dockerClient,
                serviceRegistry,
                tenant,
                project,
                stage,
                versionConfig,
                deploymentConfig,
                image);

        appendResultMapOperation.execute(path, Map.of(
                KeyEnum.VERSION_ID, result.versionId().toString(),
                KeyEnum.DEPLOYMENT_ID, result.deploymentId().toString()));
    }

    String createDeveloperToken(final URI serviceUri,
                                final String developer,
                                final String password) {
        final var developerAnonymousClient = createDeveloperAnonymousClientOperation.execute(serviceUri);

        final var request = new CreateTokenDeveloperRequest(developer, password);
        final var developerToken = developerAnonymousClient.execute(request)
                .map(CreateTokenDeveloperResponse::getRawToken)
                .await().indefinitely();

        return developerToken;
    }
}