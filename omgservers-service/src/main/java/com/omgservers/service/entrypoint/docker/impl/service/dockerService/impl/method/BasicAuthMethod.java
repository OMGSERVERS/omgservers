package com.omgservers.service.entrypoint.docker.impl.service.dockerService.impl.method;

import com.omgservers.schema.entrypoint.docker.BasicAuthDockerRequest;
import com.omgservers.schema.entrypoint.docker.BasicAuthDockerResponse;
import io.smallrye.mutiny.Uni;

public interface BasicAuthMethod {
    Uni<BasicAuthDockerResponse> basicAuth(BasicAuthDockerRequest request);
}
