package com.omgservers.testDataFactory;

import com.omgservers.model.dto.tenant.SyncVersionRequest;
import com.omgservers.model.stage.StageModel;
import com.omgservers.model.tenant.TenantModel;
import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.model.version.VersionModel;
import com.omgservers.model.version.VersionSourceCodeModel;
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
