package com.omgservers.module.tenant.impl.service.versionService.impl;

import com.omgservers.dto.tenant.BuildVersionRequest;
import com.omgservers.dto.tenant.BuildVersionResponse;
import com.omgservers.module.tenant.impl.service.versionService.VersionService;
import com.omgservers.module.tenant.impl.service.versionService.impl.method.buildVersion.BuildVersionMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class VersionServiceImpl implements VersionService {

    final BuildVersionMethod buildVersionMethod;

    @Override
    public Uni<BuildVersionResponse> buildVersion(BuildVersionRequest request) {
        return buildVersionMethod.buildVersion(request);
    }
}
