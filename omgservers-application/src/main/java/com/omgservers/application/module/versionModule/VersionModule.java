package com.omgservers.application.module.versionModule;

import com.omgservers.application.module.versionModule.impl.service.versionHelpService.VersionHelpService;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.VersionInternalService;

public interface VersionModule {
    VersionInternalService getVersionInternalService();

    VersionHelpService getVersionHelpService();
}
