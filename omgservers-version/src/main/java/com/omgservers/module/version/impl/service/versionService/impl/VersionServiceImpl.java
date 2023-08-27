package com.omgservers.module.version.impl.service.versionService.impl;

import com.omgservers.module.version.impl.service.versionService.VersionService;
import com.omgservers.module.version.impl.service.versionService.impl.method.buildVersion.BuildVersionMethod;
import com.omgservers.dto.version.BuildVersionRequest;
import com.omgservers.dto.version.BuildVersionResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class VersionServiceImpl implements VersionService {

    final BuildVersionMethod createVersionMethod;

    @Override
    public Uni<BuildVersionResponse> buildVersion(BuildVersionRequest request) {
        return createVersionMethod.buildVersion(request);
    }
}
