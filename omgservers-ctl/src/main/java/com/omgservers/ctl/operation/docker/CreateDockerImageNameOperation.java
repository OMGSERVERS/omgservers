package com.omgservers.ctl.operation.docker;

import java.net.URI;

public interface CreateDockerImageNameOperation {
    DockerImageName execute(URI registryUri, String tenant, String project, String tag);
}
