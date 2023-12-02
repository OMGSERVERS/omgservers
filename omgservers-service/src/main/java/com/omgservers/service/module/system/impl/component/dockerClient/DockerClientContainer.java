package com.omgservers.service.module.system.impl.component.dockerClient;

import com.github.dockerjava.api.DockerClient;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.Serial;
import java.util.concurrent.atomic.AtomicReference;

@ApplicationScoped
public class DockerClientContainer extends AtomicReference<DockerClient> {

    @Serial
    private static final long serialVersionUID = -8625833390361353952L;

}
