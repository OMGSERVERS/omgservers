package com.omgservers.ctl.operation.service;

import com.github.dockerjava.api.DockerClient;
import com.omgservers.ctl.client.DeveloperClient;
import com.omgservers.schema.model.deployment.DeploymentConfigDto;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;

import java.net.URI;

public interface DeployVersionOperation {

    DeployVersionResult execute(DeveloperClient developerClient,
                                DockerClient dockerClient,
                                URI registryUri,
                                String tenant,
                                String project,
                                String stage,
                                TenantVersionConfigDto versionConfig,
                                DeploymentConfigDto deploymentConfig,
                                String image);
}
