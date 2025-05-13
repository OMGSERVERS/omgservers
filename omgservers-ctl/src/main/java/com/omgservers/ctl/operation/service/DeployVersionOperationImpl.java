package com.omgservers.ctl.operation.service;

import com.github.dockerjava.api.DockerClient;
import com.omgservers.ctl.client.DeveloperClient;
import com.omgservers.ctl.operation.client.CreateDeveloperClientOperation;
import com.omgservers.ctl.operation.client.CreateLocalDeveloperClientOperation;
import com.omgservers.ctl.operation.docker.CreateDockerDaemonClientOperation;
import com.omgservers.ctl.operation.docker.CreateDockerImageNameOperation;
import com.omgservers.ctl.operation.wal.AppendResultMapOperation;
import com.omgservers.ctl.operation.wal.GetWalOperation;
import com.omgservers.ctl.operation.wal.developer.FindDeveloperTokenOperation;
import com.omgservers.ctl.operation.wal.installation.FindInstallationDetailsOperation;
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

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeployVersionOperationImpl implements DeployVersionOperation {

    final CreateLocalDeveloperClientOperation createLocalDeveloperClientOperation;
    final CreateDockerDaemonClientOperation createDockerDaemonClientOperation;
    final CreateDockerImageNameOperation createDockerImageNameOperation;
    final AppendResultMapOperation appendResultMapOperation;
    final GetWalOperation getWalOperation;

    final FindInstallationDetailsOperation findInstallationDetailsOperation;
    final CreateDeveloperClientOperation createDeveloperClientOperation;
    final FindDeveloperTokenOperation findDeveloperTokenOperation;

    @Override
    public DeployVersionResult execute(final DeveloperClient developerClient,
                                       final DockerClient dockerClient,
                                       final URI registryUri,
                                       final String tenant,
                                       final String project,
                                       final String stage,
                                       final TenantVersionConfigDto config,
                                       final String image) {
        pingDocker(dockerClient);

        final var dockerImageName = createDockerImageNameOperation.execute(registryUri,
                tenant,
                project,
                String.valueOf(System.currentTimeMillis()));
        tagImage(dockerClient, image, dockerImageName.imageNameWithRepository(), dockerImageName.tag());
        pushImage(dockerClient, dockerImageName.fullImageName(), 60L);

        final var versionId = createVersion(developerClient, tenant, project, config);
        createImage(developerClient, tenant, project, String.valueOf(versionId), dockerImageName.imageNameWithTag());
        final var deploymentId = createDeployment(developerClient, tenant, project, stage, String.valueOf(versionId));

        return new DeployVersionResult(versionId, deploymentId);
    }

    void pingDocker(final DockerClient dockerClient) {
        log.info("Ping docker daemon");
        dockerClient.pingCmd().exec();
    }

    void tagImage(final DockerClient dockerClient,
                  final String image,
                  final String imageNameWithRepository,
                  final String tag) {
        dockerClient.tagImageCmd(image, imageNameWithRepository, tag).exec();

        log.info("Image \"{}\" tagged as \"{}\"", image, imageNameWithRepository + ":" + tag);
    }

    @SneakyThrows
    void pushImage(final DockerClient dockerClient, final String image, final long timeoutInSeconds) {
        log.info("Pushing image \"{}\"...", image);

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

    Long createVersion(final DeveloperClient developerClient,
                       final String tenant,
                       final String project,
                       final TenantVersionConfigDto config) {
        final var request = new CreateVersionDeveloperRequest(tenant, project, config);
        final var versionId = developerClient.execute(request)
                .map(CreateVersionDeveloperResponse::getVersionId)
                .await().indefinitely();

        log.info("Version \"{}\" created", versionId);

        return versionId;
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