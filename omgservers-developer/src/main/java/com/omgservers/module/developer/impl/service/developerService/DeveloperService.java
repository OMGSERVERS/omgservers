package com.omgservers.module.developer.impl.service.developerService;

import com.omgservers.dto.developer.CreateProjectDeveloperRequest;
import com.omgservers.dto.developer.CreateProjectDeveloperResponse;
import com.omgservers.dto.developer.CreateTokenDeveloperRequest;
import com.omgservers.dto.developer.CreateTokenDeveloperResponse;
import com.omgservers.dto.developer.CreateVersionDeveloperRequest;
import com.omgservers.dto.developer.CreateVersionDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface DeveloperService {

    Uni<CreateTokenDeveloperResponse> createToken(CreateTokenDeveloperRequest request);

    Uni<CreateProjectDeveloperResponse> createProject(CreateProjectDeveloperRequest request);

    Uni<CreateVersionDeveloperResponse> createVersion(CreateVersionDeveloperRequest request);
}
