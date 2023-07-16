package com.omgservers.application.module.versionModule.impl.service.versionHelpService;

import com.omgservers.application.module.versionModule.impl.service.versionHelpService.request.BuildVersionHelpRequest;
import com.omgservers.application.module.versionModule.impl.service.versionHelpService.response.BuildVersionHelpResponse;
import io.smallrye.mutiny.Uni;

public interface VersionHelpService {

    Uni<BuildVersionHelpResponse> buildVersion(BuildVersionHelpRequest request);
}
