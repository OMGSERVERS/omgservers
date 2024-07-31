package com.omgservers.service.entrypoint.developer.impl.operation.mapStageDataToDashboard;

import com.omgservers.schema.entrypoint.developer.dto.StageDashboardDto;
import com.omgservers.schema.module.tenant.stage.dto.StageDataDto;

public interface MapStageDataToDashboardOperation {
    StageDashboardDto mapStageDataToDashboard(StageDataDto stageData);
}
