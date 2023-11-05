package com.omgservers.service.module.tenant.impl.service.stageService.impl.method.viewStages;

import com.omgservers.model.dto.tenant.ViewStagesRequest;
import com.omgservers.model.dto.tenant.ViewStagesResponse;
import io.smallrye.mutiny.Uni;

public interface ViewStagesMethod {
    Uni<ViewStagesResponse> viewStages(ViewStagesRequest request);
}
