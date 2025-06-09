package com.omgservers.service.server.task.impl.method.executeStageTask.dto;

import com.omgservers.schema.model.tenantStageChangeOfState.TenantStageChangeOfStateDto;

public record HandleTenantStageResult(Long tenantId,
                                      Long tenantStageId,
                                      TenantStageChangeOfStateDto tenantStageChangeOfState) {
}
