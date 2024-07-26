package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.deployVersion;

import com.omgservers.model.dto.developer.DeployVersionDeveloperRequest;
import com.omgservers.model.dto.developer.DeployVersionDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface DeployVersionMethod {
    Uni<DeployVersionDeveloperResponse> deployVersion(DeployVersionDeveloperRequest request);
}
