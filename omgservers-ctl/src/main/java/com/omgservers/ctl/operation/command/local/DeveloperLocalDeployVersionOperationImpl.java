package com.omgservers.ctl.operation.command.local;

import com.github.dockerjava.api.DockerClient;
import com.omgservers.ctl.client.DeveloperClient;
import com.omgservers.ctl.configuration.LocalConfiguration;
import com.omgservers.ctl.operation.client.CreateLocalDeveloperClientOperation;
import com.omgservers.ctl.operation.docker.CreateDockerDaemonClientOperation;
import com.omgservers.ctl.operation.docker.CreateDockerImageNameOperation;
import com.omgservers.ctl.operation.wal.AppendResultMapOperation;
import com.omgservers.ctl.operation.wal.GetWalOperation;
import com.omgservers.ctl.operation.wal.local.FindLocalTenantOperation;
import com.omgservers.schema.entrypoint.developer.CreateDeploymentDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateDeploymentDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateImageDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateImageDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateVersionDeveloperResponse;
import com.omgservers.schema.model.tenantImage.TenantImageQualifierEnum;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeveloperLocalDeployVersionOperationImpl implements DeveloperLocalDeployVersionOperation {

    final CreateLocalDeveloperClientOperation createLocalDeveloperClientOperation;
    final CreateDockerDaemonClientOperation createDockerDaemonClientOperation;
    final CreateDockerImageNameOperation createDockerImageNameOperation;
    final AppendResultMapOperation appendResultMapOperation;
    final FindLocalTenantOperation findLocalTenantOperation;
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

        final var versionId = createVersion(developerClient, tenant, project, config);

        final var dockerDaemonUri = LocalConfiguration.DOCKER_URI;
        final var registryUri = LocalConfiguration.REGISTRY_URI;

        final var dockerClient = createDockerDaemonClientOperation
                .execute(dockerDaemonUri, registryUri, developer, password);
        pingDocker(dockerClient);

        final var dockerImageName = createDockerImageNameOperation
                .execute(registryUri, tenant, project, versionId.toString());

        tagImage(dockerClient, image, dockerImageName.imageNameWithRepository(), dockerImageName.tag());
        pushImage(dockerClient, dockerImageName.fullImageName(), 60L);

        createImage(developerClient, tenant, project, String.valueOf(versionId), dockerImageName.imageNameWithTag());

        createDeployment(developerClient, tenant, project, stage, String.valueOf(versionId));
    }

    Long createVersion(final DeveloperClient developerClient,
                       final String tenant,
                       final String project,
                       final TenantVersionConfigDto config) {
        final var request = new CreateVersionDeveloperRequest(tenant, project, config);
        final var versionId = developerClient.execute(request)
                .map(CreateVersionDeveloperResponse::getVersionId)
                .await().indefinitely();

        return versionId;
    }

    void pingDocker(final DockerClient dockerClient) {
        dockerClient.pingCmd().exec();
    }

    void tagImage(final DockerClient dockerClient,
                  final String image,
                  final String imageNameWithRepository,
                  final String tag) {
        dockerClient.tagImageCmd(image, imageNameWithRepository, tag).exec();

        log.debug("Image \"{}\" tagged as \"{}\"", image, imageNameWithRepository + ":" + tag);
    }

    @SneakyThrows
    void pushImage(final DockerClient dockerClient, final String image, final long timeoutInSeconds) {
        log.debug("Pushing image \"{}\"...", image);

        dockerClient.pushImageCmd(image).start()
                .awaitCompletion(timeoutInSeconds, TimeUnit.SECONDS);
    }

    boolean createImage(final DeveloperClient developerClient,
                        final String tenant,
                        final String project,
                        final String version,
                        final String image) {
        final var request = new CreateImageDeveloperRequest(tenant,
                project,
                version,
                TenantImageQualifierEnum.UNIVERSAL,
                image);
        final var created = developerClient.execute(request)
                .map(CreateImageDeveloperResponse::getCreated)
                .await().indefinitely();
        return created;
    }

    Long createDeployment(final DeveloperClient developerClient,
                          final String tenant,
                          final String project,
                          final String stage,
                          final String version) {
        final var request = new CreateDeploymentDeveloperRequest(tenant,
                project,
                stage,
                Long.valueOf(version));
        final var deploymentId = developerClient.execute(request)
                .map(CreateDeploymentDeveloperResponse::getDeploymentId)
                .await().indefinitely();
        log.info("Deployment \"{}\" created", deploymentId);
        return deploymentId;
    }
}