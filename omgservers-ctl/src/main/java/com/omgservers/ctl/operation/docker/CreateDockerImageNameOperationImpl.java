package com.omgservers.ctl.operation.docker;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

@Slf4j
@ApplicationScoped
class CreateDockerImageNameOperationImpl implements CreateDockerImageNameOperation {

    @Override
    public DockerImageName execute(final URI registryUri,
                                   final String tenant,
                                   final String project,
                                   final String tag) {
        final var imageName = tenant + "/" + project;
        final var imageNameWithTag = tenant + "/" + project + ":" + tag;
        final String imageNameWithRepository;
        if (registryUri.getPort() == -1) {
            imageNameWithRepository = registryUri.getHost() + "/" + imageName;
        } else {
            imageNameWithRepository = registryUri.getHost() + ":" + registryUri.getPort() + "/" + imageName;
        }
        final var fullImageName = imageNameWithRepository + ":" + tag;

        return new DockerImageName(imageName, imageNameWithTag, imageNameWithRepository, fullImageName, tag);
    }
}
