package com.omgservers.service.server.task.impl.method.executeStageTask.dto;

import com.omgservers.schema.model.tenantStageState.TenantStageStateDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public record FetchTenantStageResult(Long tenantId,
                                     Long tenantStageId,
                                     TenantStageStateDto tenantStageState) {
}
