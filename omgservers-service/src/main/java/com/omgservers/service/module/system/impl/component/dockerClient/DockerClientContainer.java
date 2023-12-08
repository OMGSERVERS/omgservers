package com.omgservers.service.module.system.impl.component.dockerClient;

import com.github.dockerjava.api.DockerClient;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.concurrent.atomic.AtomicReference;

@ApplicationScoped
public class DockerClientContainer {

    final AtomicReference<DockerClient> atomicReference;

    public DockerClientContainer() {
        this.atomicReference = new AtomicReference<>();
    }

    public void set(final DockerClient dockerClient) {
        atomicReference.set(dockerClient);
    }

    public DockerClient get() {
        return atomicReference.get();
    }
}
