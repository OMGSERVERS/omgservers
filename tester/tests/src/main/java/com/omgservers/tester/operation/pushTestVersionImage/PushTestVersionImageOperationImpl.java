package com.omgservers.tester.operation.pushTestVersionImage;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Image;
import com.omgservers.tester.model.TestVersionModel;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class PushTestVersionImageOperationImpl implements PushTestVersionImageOperation {

    @Override
    public void pushTestVersionImage(final DockerClient dockerClient,
                                     final String image,
                                     final TestVersionModel testVersionModel) throws InterruptedException {
        final var imageIdOptional = getImageId(dockerClient, image);
        if (imageIdOptional.isEmpty()) {
            throw new IllegalArgumentException(image + " was not found");
        }
        final var imageId = imageIdOptional.get();

        final var tenantId = testVersionModel.getTenantId();
        final var projectId = testVersionModel.getProjectId();
        final var stageId = testVersionModel.getStageId();
        final var versionId = testVersionModel.getVersionId();
        final var repository = String.format("localhost:5000/omgservers/%s/%s/%s/universal",
                tenantId,
                projectId,
                stageId);
        dockerClient.tagImageCmd(imageId, repository, versionId.toString()).exec();

        dockerClient.pushImageCmd(repository + ":" + versionId)
                .start()
                .awaitCompletion(1, TimeUnit.MINUTES);
    }

    Optional<String> getImageId(final DockerClient dockerClient,
                                final String image) {
        final var images = dockerClient.listImagesCmd().exec();
        return images.stream().filter(i -> {
                    if (i.getRepoTags().length > 0) {
                        return (i.getRepoTags()[0]).equals(image);
                    } else {
                        return false;
                    }
                })
                .map(Image::getId)
                .findFirst();
    }
}