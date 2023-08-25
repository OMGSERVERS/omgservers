package com.omgservers.application.module.developerModule.impl.service.developerWebService;

import com.omgservers.dto.developerModule.CreateProjectDeveloperRequest;
import com.omgservers.dto.developerModule.CreateTokenDeveloperRequest;
import com.omgservers.dto.developerModule.CreateVersionDeveloperRequest;
import com.omgservers.dto.developerModule.GetVersionStatusDeveloperRequest;
import com.omgservers.dto.developerModule.CreateProjectDeveloperResponse;
import com.omgservers.dto.developerModule.CreateTokenDeveloperResponse;
import com.omgservers.dto.developerModule.CreateVersionDeveloperResponse;
import com.omgservers.dto.developerModule.GetVersionStatusDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface DeveloperWebService {

    Uni<CreateTokenDeveloperResponse> createToken(CreateTokenDeveloperRequest request);

    Uni<CreateProjectDeveloperResponse> createProject(CreateProjectDeveloperRequest request);

    Uni<CreateVersionDeveloperResponse> createVersion(CreateVersionDeveloperRequest request);

    Uni<GetVersionStatusDeveloperResponse> getVersionStatus(GetVersionStatusDeveloperRequest request);
}
