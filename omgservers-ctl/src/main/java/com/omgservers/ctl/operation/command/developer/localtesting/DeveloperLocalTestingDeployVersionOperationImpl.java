package com.omgservers.ctl.operation.command.developer.localtesting;

import com.omgservers.ctl.configuration.LocalConfiguration;
import com.omgservers.ctl.dto.key.KeyEnum;
import com.omgservers.ctl.operation.client.CreateLocalDeveloperClientOperation;
import com.omgservers.ctl.operation.docker.CreateDockerDaemonClientOperation;
import com.omgservers.ctl.operation.service.DeployVersionOperation;
import com.omgservers.ctl.operation.wal.AppendResultMapOperation;
import com.omgservers.ctl.operation.wal.GetWalOperation;
import com.omgservers.ctl.operation.wal.local.FindLocalTenantOperation;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeveloperLocalTestingDeployVersionOperationImpl implements DeveloperLocalTestingDeployVersionOperation {

    final CreateLocalDeveloperClientOperation createLocalDeveloperClientOperation;
    final CreateDockerDaemonClientOperation createDockerDaemonClientOperation;
    final AppendResultMapOperation appendResultMapOperation;
    final FindLocalTenantOperation findLocalTenantOperation;
    final DeployVersionOperation deployVersionOperation;
    final GetWalOperation getWalOperation;

    @Override
    public void execute(final String tenant,
                        final String project,
                        final String stage,
                        final TenantVersionConfigDto config,
                        final String image) {
        final var wal = getWalOperation.execute();
        final var path = wal.getPath();

        final var localTenantLog = findLocalTenantOperation.execute(wal, tenant, project, stage);

        final var developer = localTenantLog.getDeveloper();
        final var password = localTenantLog.getPassword();
        final var developerClient = createLocalDeveloperClientOperation.execute(developer, password);

        final var dockerDaemonUri = LocalConfiguration.DOCKER_URI;
        final var registryUri = LocalConfiguration.REGISTRY_URI;
        final var dockerClient = createDockerDaemonClientOperation.execute(dockerDaemonUri,
                registryUri,
                developer,
                password);

        final var result = deployVersionOperation.execute(developerClient,
                dockerClient,
                registryUri,
                tenant,
                project,
                stage,
                config,
                image);

        appendResultMapOperation.execute(path, Map.of(
                KeyEnum.VERSION_ID, result.versionId().toString(),
                KeyEnum.DEPLOYMENT_ID, result.deploymentId().toString()));
    }
}