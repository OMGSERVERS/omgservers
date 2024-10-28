package com.omgservers.service.module.docker.impl.service.dockerService.impl.method;

import com.omgservers.schema.module.docker.PingDockerHostRequest;
import com.omgservers.schema.module.docker.PingDockerHostResponse;
import io.smallrye.mutiny.Uni;

public interface PingDockerHostMethod {
    Uni<PingDockerHostResponse> execute(PingDockerHostRequest request);
}
