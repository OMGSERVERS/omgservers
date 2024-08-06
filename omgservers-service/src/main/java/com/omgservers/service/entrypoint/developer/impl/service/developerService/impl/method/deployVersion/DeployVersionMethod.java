package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.deployVersion;

import com.omgservers.schema.entrypoint.developer.DeployVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeployVersionDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface DeployVersionMethod {
    Uni<DeployVersionDeveloperResponse> deployVersion(DeployVersionDeveloperRequest request);
}
