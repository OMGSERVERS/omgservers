package com.omgservers.service.entrypoint.developer.impl.operation.mapVersionDataToDashboard;

import com.omgservers.schema.entrypoint.developer.dto.VersionDashboardDto;
import com.omgservers.schema.module.tenant.version.dto.VersionDataDto;

public interface MapVersionDataToDashboardOperation {
    VersionDashboardDto mapVersionDataToDashboard(VersionDataDto versionData);
}
