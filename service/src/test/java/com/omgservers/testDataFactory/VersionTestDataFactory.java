package com.omgservers.testDataFactory;

import com.omgservers.service.factory.tenant.VersionModelFactory;
import com.omgservers.service.module.tenant.impl.service.versionService.testInterface.VersionServiceTestInterface;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class VersionTestDataFactory {

    final VersionServiceTestInterface versionService;

    final VersionModelFactory versionModelFactory;

}
