package com.omgservers.application.module.versionModule.impl.service.versionHelpService.impl.method.buildVersionMethod;

import com.omgservers.application.module.versionModule.impl.service.versionHelpService.request.BuildVersionHelpRequest;
import com.omgservers.application.module.versionModule.impl.service.versionHelpService.response.BuildVersionHelpResponse;
import io.smallrye.mutiny.Uni;

public interface BuildVersionMethod {
    Uni<BuildVersionHelpResponse> buildVersion(BuildVersionHelpRequest request);
}
