package com.omgservers.module.version;

import com.omgservers.module.version.impl.service.versionService.VersionService;
import com.omgservers.module.version.impl.service.versionShardedService.VersionShardedService;

public interface VersionModule {
    VersionShardedService getVersionShardedService();

    VersionService getVersionService();
}
