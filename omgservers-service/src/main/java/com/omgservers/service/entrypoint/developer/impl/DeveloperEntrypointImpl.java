package com.omgservers.service.entrypoint.developer.impl;

import com.omgservers.service.entrypoint.developer.DeveloperEntrypoint;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.DeveloperService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeveloperEntrypointImpl implements DeveloperEntrypoint {

    final DeveloperService developerService;

    @Override
    public DeveloperService getDeveloperService() {
        return developerService;
    }
}
