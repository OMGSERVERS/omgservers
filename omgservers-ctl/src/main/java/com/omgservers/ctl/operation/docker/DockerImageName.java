package com.omgservers.ctl.operation.docker;

public record DockerImageName(String imageName,
                              String imageNameWithTag,
                              String imageNameWithRepository,
                              String fullImageName,
                              String tag) {
}
