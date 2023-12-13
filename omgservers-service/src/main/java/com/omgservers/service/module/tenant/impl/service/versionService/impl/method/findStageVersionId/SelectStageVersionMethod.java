package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.findStageVersionId;

import com.omgservers.model.dto.tenant.SelectStageVersionRequest;
import com.omgservers.model.dto.tenant.SelectStageVersionResponse;
import io.smallrye.mutiny.Uni;

public interface SelectStageVersionMethod {

    Uni<SelectStageVersionResponse> selectStageVersion(SelectStageVersionRequest request);
}
