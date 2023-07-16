package com.omgservers.application.module.developerModule.impl.service.developerHelpService;

import com.omgservers.application.module.developerModule.impl.service.developerHelpService.request.CreateProjectHelpRequest;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.request.CreateTokenHelpRequest;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.request.CreateVersionHelpRequest;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.request.GetVersionStatusHelpRequest;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.response.CreateProjectHelpResponse;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.response.CreateTokenHelpResponse;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.response.CreateVersionHelpResponse;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.response.GetVersionStatusHelpResponse;
import io.smallrye.mutiny.Uni;

public interface DeveloperHelpService {

    Uni<CreateTokenHelpResponse> createToken(CreateTokenHelpRequest request);

    Uni<CreateProjectHelpResponse> createProject(CreateProjectHelpRequest request);

    Uni<CreateVersionHelpResponse> createVersion(CreateVersionHelpRequest request);

    Uni<GetVersionStatusHelpResponse> getVersionStatus(GetVersionStatusHelpRequest request);
}
