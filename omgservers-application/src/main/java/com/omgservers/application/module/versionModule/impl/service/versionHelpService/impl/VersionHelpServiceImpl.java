package com.omgservers.application.module.versionModule.impl.service.versionHelpService.impl;

import com.omgservers.application.module.versionModule.impl.service.versionHelpService.VersionHelpService;
import com.omgservers.application.module.versionModule.impl.service.versionHelpService.impl.method.buildVersionMethod.BuildVersionMethod;
import com.omgservers.application.module.versionModule.impl.service.versionHelpService.request.BuildVersionHelpRequest;
import com.omgservers.application.module.versionModule.impl.service.versionHelpService.response.BuildVersionHelpResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class VersionHelpServiceImpl implements VersionHelpService {

    final BuildVersionMethod createVersionMethod;

    @Override
    public Uni<BuildVersionHelpResponse> buildVersion(BuildVersionHelpRequest request) {
        return createVersionMethod.buildVersion(request);
    }
}
