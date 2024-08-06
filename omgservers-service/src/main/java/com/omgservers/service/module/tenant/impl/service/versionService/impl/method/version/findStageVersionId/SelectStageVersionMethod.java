package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.version.findStageVersionId;

import com.omgservers.schema.module.tenant.SelectStageVersionRequest;
import com.omgservers.schema.module.tenant.SelectStageVersionResponse;
import io.smallrye.mutiny.Uni;

public interface SelectStageVersionMethod {

    Uni<SelectStageVersionResponse> selectStageVersion(SelectStageVersionRequest request);
}
