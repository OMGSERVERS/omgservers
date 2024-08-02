package com.omgservers.tester.operation.pushTestVersionImage;

import com.github.dockerjava.api.DockerClient;
import com.omgservers.tester.dto.TestVersionDto;

public interface PushTestVersionImageOperation {

    void pushTestVersionImage(DockerClient dockerClient,
                              String image,
                              TestVersionDto testVersionDto) throws InterruptedException;
}
