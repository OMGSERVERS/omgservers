package com.omgservers.application.module.versionModule;

import com.omgservers.application.module.versionModule.impl.service.versionInternalService.VersionInternalService;
import com.omgservers.application.module.versionModule.impl.service.versionHelpService.VersionHelpService;

public interface VersionModule {
    VersionInternalService getVersionInternalService();

    VersionHelpService getVersionHelpService();
}
