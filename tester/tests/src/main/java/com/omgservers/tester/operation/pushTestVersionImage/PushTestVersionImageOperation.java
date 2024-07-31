package com.omgservers.tester.operation.pushTestVersionImage;

import com.github.dockerjava.api.DockerClient;
import com.omgservers.tester.model.TestVersionModel;

public interface PushTestVersionImageOperation {

    void pushTestVersionImage(DockerClient dockerClient,
                              String image,
                              TestVersionModel testVersionModel) throws InterruptedException;
}
