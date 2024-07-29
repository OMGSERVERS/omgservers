package com.omgservers.service.module.tenant.impl.service.stageService.impl.method.stage.viewStages;

import com.omgservers.schema.module.tenant.ViewStagesRequest;
import com.omgservers.schema.module.tenant.ViewStagesResponse;
import io.smallrye.mutiny.Uni;

public interface ViewStagesMethod {
    Uni<ViewStagesResponse> viewStages(ViewStagesRequest request);
}
